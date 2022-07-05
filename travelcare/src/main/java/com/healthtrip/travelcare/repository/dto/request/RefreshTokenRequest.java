package com.healthtrip.travelcare.repository.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshTokenRequest {
    private Long tokenId;
    private String refreshToken;
}
