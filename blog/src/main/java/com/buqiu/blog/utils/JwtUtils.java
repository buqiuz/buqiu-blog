package com.buqiu.blog.utils;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-04 20:57:18
 */

@Component
public class JwtUtils {
    // 过期时间 单位为小时
    @Value("${jwt.expire-time:24}")
    private long EXPIRE_TIME;

    KeyPair keyPair = Jwts.SIG.EdDSA.keyPair().build(); //or RS384, RS512, PS256, etc...

    public String createToken(Map<String, Object> payload) {
        return  Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "EdDSA")
                .and()
                .issuer("buqiu")
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME*1000*60*60))
                .issuedAt(new Date())
                .claims(payload)
                .signWith(keyPair.getPrivate())
                .compact();

    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(keyPair.getPublic())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}