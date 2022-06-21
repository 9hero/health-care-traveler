package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.RefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
//@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Key key;
    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
    }
    private final long accessExpireTime = 10 * 60 * 1000L;

    private final long refreshExpireTime = 60 * 60 * 1000L;

    public String issueAccessToken(Account account){
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpireTime);

//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .setHeader(Map.of(
                        "typ", "JWT",
                        "alg", "HS256"
                ))
                .setSubject(account.getEmail())
                .setClaims(Map.of(
                        "userId",account.getId(),
                        "email",account.getEmail()
                ))
                .setExpiration(expiration)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }
    public RefreshToken issueRefreshToken(Account account) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + refreshExpireTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(expiration);
        String jwt = Jwts
                .builder()
                .setHeader(Map.of(
                        "typ", "JWT",
                        "alg", "HS256"
                ))
                .setClaims(Map.of(
                        "userId",account.getId(),
                        "email",account.getEmail()
                ))
                .setSubject(account.getEmail())
                .setExpiration(expiration)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        Map<String, String> result = new HashMap<>();
        var a = RefreshToken.builder()
                        .refreshToken(jwt)
                .expiration(expiration) // 날짜 정하기
                                .build();
        result.put("refreshToken",jwt);
        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return a;
    }
    public String getUserEmail(String token) {
        return (String)Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("email");
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            log.info("JWT VALID FAIL msg: {}  cause : {}",e.getMessage(),e.getCause());
            throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
    }
}
