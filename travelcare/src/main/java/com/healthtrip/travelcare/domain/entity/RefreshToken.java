package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.RefreshTokenRepository;
import com.healthtrip.travelcare.repository.dto.request.RefreshTokenRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class RefreshToken extends BaseTimeEntity{

    private final RefreshTokenRepository refreshTokenRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String refreshToken;
    private LocalDateTime expiration;

    @Autowired
    @Builder
    public RefreshToken(Long id, String refreshToken, LocalDateTime expiration,
                        RefreshTokenRepository refreshTokenRepository,
                        Long userId) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userId = userId;
    }

    public static RefreshToken toEntity(RefreshTokenRequest request) {
        return RefreshToken.builder()
                .refreshToken(request.getRefreshToken())
                .expiration(request.getExpiration())
                .build();

    }


    public RefreshToken save() {
        return refreshTokenRepository.save(this);
    }

}
