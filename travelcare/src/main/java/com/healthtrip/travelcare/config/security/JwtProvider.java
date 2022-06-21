package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.domain.entity.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
//@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private final Key key = Keys.hmacShaKeyFor("c88d74ba-1554-48a4-b549-b926f5d77c9e"
            .getBytes(StandardCharsets.UTF_8));
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
    public String getUserInfo(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("email");
    }
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");// 헤더명 합의 필요
    }
    public boolean validateJwtToken(ServletRequest request, String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("JWT MALFORMED : {}."+e.getMessage());
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (Exception e){
            e.printStackTrace();
            log.info("JWT VALID FAIL : "+e.getMessage());
        }
        return false;
    }
}
