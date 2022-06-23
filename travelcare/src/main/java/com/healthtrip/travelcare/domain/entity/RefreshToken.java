package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.RefreshTokenRepository;
import com.healthtrip.travelcare.repository.dto.request.RefreshTokenRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
public class RefreshToken extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String refreshToken;
    private LocalDateTime expirationLDT;

    @Transient
    private Date expirationDate;

    @Autowired
    @Builder
    public RefreshToken(Long id, Long userId, String refreshToken, LocalDateTime expirationLDT, Date expirationDate) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expirationLDT = expirationLDT;
        this.expirationDate = expirationDate;
    }
}
