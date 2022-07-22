package com.healthtrip.travelcare.config.security.jwt;

import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.domain.entity.account.Account;
import com.healthtrip.travelcare.domain.entity.account.RefreshToken;
import com.healthtrip.travelcare.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Key key;
    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
    }

    private final RefreshTokenRepository refreshTokenRepository;

    private final long accessExpireTime = 30 * 60 * 1000L;  // 10 min

    private final long refreshExpireTime = 60 * 60 * 1000L; // 1 hour

    public String issueAccessToken(Account account){
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpireTime);

//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .setHeader(Map.of(
                        "typ", "JWT",
                        "alg", "HS256"
                ))
                .setClaims(Map.of(
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

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        String refreshTokenExpirationAt = simpleDateFormat.format(expiration);
        String jwt = Jwts
                .builder()
                .setHeader(Map.of(
                        "typ", "JWT",
                        "alg", "HS256"
                ))
                .setClaims(Map.of(
                        "userId",account.getId()
                ))
                .setSubject(account.getEmail())
                .setExpiration(expiration)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        RefreshToken refreshToken = RefreshToken.builder()
                .account(account)
                .refreshToken(jwt)
                .expirationTransient(expiration)
                // DB 관리용 만료일 왜 쓰는지는 모름 지금 추가하는 이유는 db에서 직접 삭제하기 위함
                .expirationLDT(LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault()))
                        .build();

        return refreshToken;
    }
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
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
            log.info("JWT VALID FAIL msg: {}  cause : {}",e.getMessage(),e.getCause());
            throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            log.info("JWT VALID FAIL msg: {}  cause : {}",e.getMessage(),e.getCause());
            throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
    }
}
