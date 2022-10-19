package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "투어 추가 옵션 예약사항")
public class ReservationTourOptionsRes implements Serializable {
    private Long reservationTourOptionId;
    private String day;
    private Short manCount;
    private String requesterName;
    private String optionName;
    private BigDecimal optionAmount;
    public static ReservationTourOptionsRes toResponse(ReservationTourOptions reservationTourOptions) {
        return ReservationTourOptionsRes.builder()
                .reservationTourOptionId(reservationTourOptions.getId())
                .day(reservationTourOptions.getDay())
                .manCount(reservationTourOptions.getManCount())
                .requesterName(reservationTourOptions.getRequesterName())
                .optionAmount(reservationTourOptions.getAmount())
                .build();
    }
}
