package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.domain.entity.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final long accessExpireTime = 10 * 60 * 1000L;

    private final long refreshExpireTime = 60 * 60 * 1000L;

    public String issueAccessToken(Account account){
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpireTime);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .setHeader(Map.of(
                        "typ", "JWT",
                        "alg", "HS256"
                ))
                .setSubject(account.getEmail())
                .setClaims(Map.of("userId",account.getId()))
                .setExpiration(expiration)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }
    public Map<String, String> issueRefreshToken(Account account) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + refreshExpireTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(expiration);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts
                .builder()
                .setHeader(Map.of(
                        "typ", "JWT",
                        "alg", "HS256"
                ))
                .setClaims(Map.of("userId",account.getId()))
                .setSubject(account.getEmail())
                .setExpiration(expiration)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        Map<String, String> result = new HashMap<>();
        result.put("refreshToken",jwt);
        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return result;
    }
}
