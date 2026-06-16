package com.catoj.server.service.Iml;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.catoj.common.utils.MailUtils;
import com.catoj.server.service.IMailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.catoj.common.constants.RedisConstant.Mail.*;

/**
 * 邮件服务类
 *
 * @author Catoj
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;


    /**
     * 发送验证码
     */
    @Override
    public void sendVerifyCode(String toEmail) {
        // 1. 检查发送频率
        String intervalKey = StrUtil.format(INTERVAL_PREFIX, toEmail);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(intervalKey))) {
            Long ttl = redisTemplate.getExpire(intervalKey, TimeUnit.SECONDS);
            throw new RuntimeException("请勿频繁发送，请 " + ttl + " 秒后再试");
        }

        // 2. 生成验证码
        String code = RandomUtil.randomString(6);

        // 3. 存入 Redis
        String codeKey = StrUtil.format(CODE_PREFIX, toEmail);;
        redisTemplate.opsForValue().set(codeKey, code, EXPIRE_MINUTES, TimeUnit.MINUTES);

        // 4. 设置发送间隔
        redisTemplate.opsForValue().set(intervalKey, "1", SEND_INTERVAL_SECONDS, TimeUnit.SECONDS);

        // 5. 发送邮件（调用工具类构建内容）
        try {
            String content = MailUtils.buildVerifyCodeEmail(code, (int) EXPIRE_MINUTES);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("【Catoj】邮箱验证码");
            helper.setText(content, true);
            mailSender.send(message);

            log.info("验证码发送成功: {} -> {}", toEmail, code);
        } catch (Exception e) {
            // 发送失败，清除 Redis 记录
            redisTemplate.delete(codeKey);
            redisTemplate.delete(intervalKey);
            log.error("验证码发送失败: {}", e.getMessage());
            throw new RuntimeException("验证码发送失败，请稍后重试");
        }
    }

    /**
     * 验证验证码
     */
    @Override
    public void verifyCode(String email, String code) {
        // 格式校验
        if (!MailUtils.isValidCode(code)) {
            throw new RuntimeException("验证码格式错误");
        }

        String codeKey = StrUtil.format(CODE_PREFIX, email);
        String savedCode = redisTemplate.opsForValue().get(codeKey);

        if (savedCode == null) {
            throw new RuntimeException("验证码已过期，请重新获取");
        }

        // 检查错误次数
        String errorKey = StrUtil.format(ERROR_PREFIX, email);
        Long errorCount = redisTemplate.opsForValue().increment(errorKey);
        if (errorCount != null && errorCount > MAX_ERROR_TIMES) {
            redisTemplate.delete(codeKey);
            throw new RuntimeException("验证码错误次数过多，请重新获取");
        }
        redisTemplate.expire(errorKey, 10, TimeUnit.MINUTES);

        // 验证
        if (!savedCode.equals(code)) {
            int remainTimes = MAX_ERROR_TIMES - errorCount.intValue() + 1;
            throw new RuntimeException("验证码错误，还剩 " + Math.max(0, remainTimes) + " 次机会");
        }

        // 验证成功，清除所有记录
        redisTemplate.delete(codeKey);
        redisTemplate.delete(errorKey);
        log.info("验证码验证成功: {}", email);
    }

    /**
     * 发送普通文本邮件
     */
    @Override
    public void sendTextMail(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, false);
            mailSender.send(message);
            log.info("邮件发送成功: {}", toEmail);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
    }
}