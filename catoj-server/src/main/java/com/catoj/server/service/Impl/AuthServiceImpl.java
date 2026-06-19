package com.catoj.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.catoj.common.constants.StatusConstant;
import com.catoj.common.exception.BusinessException;
import com.catoj.common.utils.JwtUtils;
import com.catoj.common.utils.PasswordUtils;
import com.catoj.common.utils.ThreadLocalUtil;
import com.catoj.pojo.dto.*;
import com.catoj.pojo.entity.User;
import com.catoj.pojo.vo.LoginVO;
import com.catoj.server.mapper.UserMapper;
import com.catoj.server.service.IAuthService;
import com.catoj.server.service.IMailService;
import io.jsonwebtoken.Claims;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.catoj.common.constants.RedisConstant.Auth.REFRESH_TOKEN_BLACKLIST;
import static com.catoj.common.constants.RedisConstant.Auth.REFRESH_TOKEN_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IMailService mailService;
    private final UserMapper userMapper;

    private final JwtUtils jwtUtils;
    private final StringRedisTemplate redisTemplate;

    /**
     * 用户注册
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterDTO dto) {
        // 1. 校验验证码
        mailService.verifyCode(dto.getEmail(), dto.getCode());

        // 2. 检查邮箱是否已被注册（二次确认，防止并发）
        LambdaQueryWrapper<User> emailWrapper = new LambdaQueryWrapper<>();
        emailWrapper.eq(User::getEmail, dto.getEmail());
        Long emailCount = userMapper.selectCount(emailWrapper);
        if (emailCount > 0) {
            throw new BusinessException("邮箱已被注册");
        }

        // 3. 检查用户名是否已被使用
        LambdaQueryWrapper<User> nameWrapper = new LambdaQueryWrapper<>();
        nameWrapper.eq(User::getName, dto.getName());
        Long nameCount = userMapper.selectCount(nameWrapper);
        if (nameCount > 0) {
            throw new BusinessException("用户名已被使用");
        }

        // 4. 创建用户
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(PasswordUtils.encode(dto.getPassword()));  // ✅ 密码加密

        // 5. 保存到数据库
        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new BusinessException("注册失败，请稍后重试");
        }

        log.info("用户注册成功: email={}, name={}, id={}",
                user.getEmail(), user.getName(), user.getId());
    }


    @Override
    public void sendCodeAndCheck(SendCodeDTO dto) {
        log.info("邮箱验证码接口");
        String email = dto.getEmail();

        // 1. 检查邮箱是否已被注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException("邮箱已被注册");
        }

        // 2. 发送验证码
        mailService.sendVerifyCode(email);
    }

    // ==================== 用户端登录 ====================

    @Override
    public LoginVO userLogin(LoginDTO dto) {
        User user = findUser(dto.getAccount());

        if (!PasswordUtils.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }

        if (user.getStatus() == StatusConstant.User.BANNED) {
            throw new BusinessException("账号已被封禁");
        }

        if (user.getRole() != 0) {
            throw new BusinessException("该账号为管理员账号，请在管理员端登录");
        }

        return generateLoginVO(user);
    }

    // ==================== 管理员端登录 ====================

    @Override
    public LoginVO adminLogin(LoginDTO dto) {
        User user = findUser(dto.getAccount());

        if (!PasswordUtils.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }

        if (user.getStatus() == StatusConstant.User.BANNED) {
            throw new BusinessException("账号已被封禁");
        }

        if (user.getRole() != 1) {
            throw new BusinessException("该账号为普通用户，请在用户端登录");
        }

        return generateLoginVO(user);
    }

    // ==================== 刷新 Token ====================

    @Override
    public LoginVO refresh(RefreshDTO dto) {
        String refreshToken = dto.getRefreshToken();

        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            throw new BusinessException("Refresh Token 无效或已过期");
        }

        String jti = jwtUtils.getRefreshTokenJti(refreshToken);

        // 检查黑名单
        String blacklistKey = REFRESH_TOKEN_BLACKLIST + jti;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(blacklistKey))) {
            throw new BusinessException("Refresh Token 已被撤销，请重新登录");
        }

        // 检查白名单
        String refreshKey = REFRESH_TOKEN_PREFIX + jti;
        String userIdStr = redisTemplate.opsForValue().get(refreshKey);
        if (userIdStr == null) {
            throw new BusinessException("Refresh Token 已过期或无效，请重新登录");
        }

        Claims claims = jwtUtils.parseRefreshToken(refreshToken);
        Long userId = claims.get("userId", Long.class);

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (user.getStatus() == StatusConstant.User.BANNED) {
            throw new BusinessException("账号已被封禁");
        }

        // 生成新 Token
        String newAccessToken = jwtUtils.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        String newRefreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getEmail());
        String newJti = jwtUtils.getRefreshTokenJti(newRefreshToken);
        Long newExpireSeconds = jwtUtils.getRemainingSeconds(newRefreshToken, false);

        // 存储新 Refresh Token
        String newRefreshKey = REFRESH_TOKEN_PREFIX + newJti;
        redisTemplate.opsForValue().set(newRefreshKey, String.valueOf(user.getId()), newExpireSeconds, TimeUnit.SECONDS);

        // 撤销旧 Refresh Token
        Long oldExpireSeconds = jwtUtils.getRemainingSeconds(refreshToken, false);
        if (oldExpireSeconds > 0) {
            redisTemplate.opsForValue().set(blacklistKey, "revoked", oldExpireSeconds, TimeUnit.SECONDS);
        }
        redisTemplate.delete(refreshKey);

        log.info("Token 刷新成功: userId={}", userId);

        return LoginVO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessExpiresIn(jwtUtils.getRemainingSeconds(newAccessToken, true))
                .refreshExpiresIn(newExpireSeconds)
                .build();
    }

    // ==================== 登出 ====================

    @Override
    public void logout(LogoutDTO dto) {
        Long userId = ThreadLocalUtil.getUserId();

        if (dto != null && dto.getRefreshToken() != null && !dto.getRefreshToken().isEmpty()) {
            String refreshToken = dto.getRefreshToken();
            String jti = jwtUtils.getRefreshTokenJti(refreshToken);

            if (jti != null) {
                String blacklistKey = REFRESH_TOKEN_BLACKLIST + jti;
                Long expireSeconds = jwtUtils.getRemainingSeconds(refreshToken, false);
                if (expireSeconds > 0) {
                    redisTemplate.opsForValue().set(blacklistKey, "revoked", expireSeconds, TimeUnit.SECONDS);
                }

                String refreshKey = REFRESH_TOKEN_PREFIX + jti;
                redisTemplate.delete(refreshKey);
            }
        }

        log.info("登出成功: userId={}", userId);
    }

    // ==================== 私有方法 ====================

    private User findUser(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, account)
                .or()
                .eq(User::getName, account);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("账号或密码错误");
        }
        return user;
    }

    private LoginVO generateLoginVO(User user) {
        String accessToken = jwtUtils.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getEmail());

        String jti = jwtUtils.getRefreshTokenJti(refreshToken);
        String refreshKey = REFRESH_TOKEN_PREFIX + jti;
        Long expireSeconds = jwtUtils.getRemainingSeconds(refreshToken, false);
        redisTemplate.opsForValue().set(refreshKey, String.valueOf(user.getId()), expireSeconds, TimeUnit.SECONDS);

        log.info("登录成功: userId={}, role={}", user.getId(), user.getRole());

        return LoginVO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpiresIn(jwtUtils.getRemainingSeconds(accessToken, true))
                .refreshExpiresIn(expireSeconds)
                .build();
    }
}
