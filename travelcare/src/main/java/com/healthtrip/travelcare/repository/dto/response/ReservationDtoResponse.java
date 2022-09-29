package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.reservation.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link com.healthtrip.travelcare.entity.reservation.Reservation} entity
 */
@Builder
@Data
@AllArgsConstructor
public class ReservationDtoResponse implements Serializable {

    private final String id;
    private final String title;
    private final BigDecimal amount;
    private final Short manCount;
    private final Reservation.Status status;
    private final Reservation.PaymentStatus paymentStatus;
    private final LocalDateTime reservedTime;

    public static ReservationDtoResponse toResponse(Reservation reservation) {
        return ReservationDtoResponse.builder()
                .id(reservation.getId())
                .title(reservation.getTitle())
                .amount(reservation.getAmount())
                .manCount(reservation.getManCount())
                .status(reservation.getStatus())
                .paymentStatus(reservation.getPaymentStatus())
//                .reservedTime()
                .build();
    }

    @Schema(name = "예약 상세 dto")
    @Builder
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RVDetails implements Serializable{
        private ReservationDtoResponse reservationDtoResponse;
        private TourReservationDtoResponse tourReservationDtoResponse;
        private HospitalReservationDtoResponse hospitalReservationDtoResponse;
        private List<ReservationPersonResponse.rpInfo> bookerInfoList;
        private List<ReservationTourOptionsRes> tourOptions;
    }


}