package com.healthtrip.travelcare.repository.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class RefreshTokenRequest {
    private Long tokenId;
    private String refreshToken;
}
