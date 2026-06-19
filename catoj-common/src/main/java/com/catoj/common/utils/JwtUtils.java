package com.catoj.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtils {

    @Value("${catoj.jwt.access.secret}")
    private String accessSecret;

    @Value("${catoj.jwt.access.expiration:900}")
    private Long accessExpire;

    @Value("${catoj.jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${catoj.jwt.refresh.expiration:604800}")
    private Long refreshExpire;

    @Getter
    @Value("${catoj.jwt.access.header:Authorization}")
    private String header;

    @Getter
    @Value("${catoj.jwt.access.prefix:Bearer}")
    private String tokenPrefix;

    // ==================== 生成 Access Token ====================

    public String generateAccessToken(Long userId, String email, Integer role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("role", role);
        claims.put("type", "access");
        return generateToken(claims, accessSecret, accessExpire);
    }

    // ==================== 生成 Refresh Token ====================

    public String generateRefreshToken(Long userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("type", "refresh");
        claims.put("jti", UUID.randomUUID().toString());
        return generateToken(claims, refreshSecret, refreshExpire);
    }

    private String generateToken(Map<String, Object> claims, String secret, Long expireSeconds) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expireSeconds * 1000))
                .signWith(key)
                .compact();
    }

    // ==================== 解析 Token ====================

    public Claims parseAccessToken(String token) {
        return parseToken(token, accessSecret);
    }

    public Claims parseRefreshToken(String token) {
        return parseToken(token, refreshSecret);
    }

    private Claims parseToken(String token, String secret) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ==================== 验证 Token ====================

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshSecret);
    }

    private boolean validateToken(String token, String secret) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("Token 验证失败: {}", e.getMessage());
            return false;
        }
    }

    // ==================== 获取信息 ====================

    public Long getUserIdFromToken(String token, boolean isAccess) {
        try {
            Claims claims = isAccess ? parseAccessToken(token) : parseRefreshToken(token);
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmailFromToken(String token, boolean isAccess) {
        try {
            Claims claims = isAccess ? parseAccessToken(token) : parseRefreshToken(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getRoleFromToken(String token) {
        try {
            Claims claims = parseAccessToken(token);
            return claims.get("role", Integer.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getRefreshTokenJti(String token) {
        try {
            Claims claims = parseRefreshToken(token);
            return claims.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getRemainingSeconds(String token, boolean isAccess) {
        try {
            Claims claims = isAccess ? parseAccessToken(token) : parseRefreshToken(token);
            long remaining = (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
            return Math.max(0, remaining);
        } catch (Exception e) {
            return 0L;
        }
    }

}