package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationInfoResponse {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(name = "나의 예약 정보 보기 Response")
    public static class MyInfo {
//        private List<ReservationPersonResponse.rpInfo> reservationPeople;
        @Schema(description = "예약고유번호")
        private String reservationInfoId;

        private String personName;
        private String packageTitle;
        private BigDecimal price;
        private ReservationInfo.Status status;
        @Schema(description = "예약한 인원수")
        private short personCount;
        private LocalDateTime departAt;
        private LocalDateTime arrivedAt;
    }
}
