package com.healthtrip.travelcare.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


public class ReservationDateResponse {

    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "예약가능일자 모든정보 Response")
    public static class DateInfoAll {

        private Long id ;

        private LocalDateTime departAt;

        private LocalDateTime arriveAt;

        private short currentNumPeople;

        private short peopleLimit;
    }
}
