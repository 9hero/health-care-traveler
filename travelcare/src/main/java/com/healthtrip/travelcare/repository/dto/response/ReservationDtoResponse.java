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
@NoArgsConstructor
public class ReservationDtoResponse implements Serializable {

    private String id;
    private String title;
    private BigDecimal amount;
    private Short manCount;
    private Reservation.Status status;
    private Reservation.PaymentStatus paymentStatus;
    private LocalDateTime reservedTime;
    private String rejection;

    public static ReservationDtoResponse toResponse(Reservation reservation) {
        return ReservationDtoResponse.builder()
                .id(reservation.getId())
                .title(reservation.getTitle())
                .amount(reservation.getAmount())
                .manCount(reservation.getManCount())
                .status(reservation.getStatus())
                .paymentStatus(reservation.getPaymentStatus())
//                .reservedTime()
//                .rejection(reservation.getReservationRejection().get(0).getReason())
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

    }


}