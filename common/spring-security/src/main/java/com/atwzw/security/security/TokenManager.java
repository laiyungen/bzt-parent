package com.atwzw.security.security;

import com.atwzw.security.entity.SecurityUser;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token管理
 */
@Component
public class TokenManager {
    // 密钥
    private String tokenSignKey = "123456";
    // 过期时间：设置默认一天(单位毫秒)
    private long tokenExpiration = 24 * 60 * 60 * 1000;

    /**
     * 生成 token 令牌
     *
     * @param username：用户名
     * @return 令牌
     */
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /**
     * 解析 token：从令牌中获取用户名
     *
     * @param token：令牌
     * @return 用户名
     */
    public String getUserFromToken(String token) {
        String username = null;
        try {
            username = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            new Throwable(e);
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token：令牌
     * @return：是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            new Throwable(e);
        }
        return false;
    }

    /**
     * 刷新令牌
     *
     * @param token：原令牌
     * @return：新令牌
     */
    public String refreshToken(String token) {
        String refreshToken;
        try {
            String username = this.getUserFromToken(token);
            refreshToken = this.createToken(username);
        } catch (Exception e) {
            refreshToken = null;
        }
        return refreshToken;
    }

    /**
     * 验证令牌
     *
     * @param token：令牌
     * @param userDetails：用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser securityUser = (SecurityUser) userDetails;
        String username = this.getUserFromToken(token);
        return (username.equals(securityUser.getUsername()) && !this.isTokenExpired(token));
    }

}
