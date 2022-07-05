package com.healthtrip.travelcare.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class CustomTravelResponse {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "커스텀 여행 제목")
    public static class Title {
        private Long customReservationId;
        private String title;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema
    public static class Info{
        @Schema(description = "커스텀예약 id")
        private Long customReserveId;

//        @Schema(description = "패키지예약 id")
//        private Long ReservationInfoId;

        private String title;
        private String question;
        private String answer;

    }

}
