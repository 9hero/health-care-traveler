package com.healthtrip.travelcare.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class TripPackageFileResponseDto {

    @Schema
    @Data
    @Builder
    public static class mainPagePackImage {
        private Long id;

        private String url;
    }
}
