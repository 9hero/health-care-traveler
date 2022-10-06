package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "투어 추가 옵션 예약사항")
public class ReservationTourOptionsRes implements Serializable {
    private Long tourOptionId;
    private String day;
    private Short manCount;
    private String requesterName;
    private String optionName;
    public static ReservationTourOptionsRes toResponse(ReservationTourOptions reservationTourOptions) {
        return ReservationTourOptionsRes.builder()
                .tourOptionId(reservationTourOptions.getId())
                .day(reservationTourOptions.getDay())
                .manCount(reservationTourOptions.getManCount())
                .requesterName(reservationTourOptions.getRequesterName())
                .build();
    }
}
