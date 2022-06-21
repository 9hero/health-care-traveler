package com.healthtrip.travelcare.repository.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshTokenRequest {
    private Long id;
    private String refreshToken;
    private LocalDateTime expiration;
}
