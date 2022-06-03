package com.healthtrip.travelcare.repository.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReservationDateRequest {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "여행일자 추가 요청")
    public static class AddTrip {
        private Long tripPackageId;

        private LocalDateTime departAt;

        private LocalDateTime arriveAt;

        private short currentNumPeople;

        private short peopleLimit;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "여행일자 수정 요청")
    public static class Modify {
        private Long reservationDateId;

        private LocalDateTime departAt;

        private LocalDateTime arriveAt;

        private short currentNumPeople;

        private short peopleLimit;
    }
}
