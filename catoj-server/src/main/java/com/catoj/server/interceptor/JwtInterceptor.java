package com.catoj.server.interceptor;

import com.catoj.common.utils.JwtUtils;
import com.catoj.common.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(jwtUtils.getHeader());
        if (token == null || !token.startsWith(jwtUtils.getTokenPrefix())) {
            throw new RuntimeException("未登录，请先登录");
        }

        token = token.substring(jwtUtils.getTokenPrefix().length()).trim();

        if (!jwtUtils.validateAccessToken(token)) {
            throw new RuntimeException("Token 无效或已过期，请重新登录");
        }

        Claims claims = jwtUtils.parseAccessToken(token);
        ThreadLocalUtil.setUserId(claims.get("userId", Long.class));
        ThreadLocalUtil.setEmail(claims.get("email", String.class));
        ThreadLocalUtil.setRole(claims.get("role", Integer.class));

        log.debug("JWT 解析成功: userId={}", ThreadLocalUtil.getUserId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadLocalUtil.clear();
    }
}