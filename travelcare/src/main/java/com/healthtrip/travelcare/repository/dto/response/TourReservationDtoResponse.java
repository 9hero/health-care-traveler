package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.healthtrip.travelcare.entity.tour.reservation.TourReservation} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourReservationDtoResponse implements Serializable {
    private Long packageId;
    private BigDecimal amount;
    private Short manCount;
    private LocalDateTime tourReservedTime;

    public static TourReservationDtoResponse toResponse(TourReservation tourReservation) {
        return TourReservationDtoResponse.builder()
                .packageId(tourReservation.getId())
                .amount(tourReservation.getAmount())
                .manCount(tourReservation.getPersonCount())
                .tourReservedTime(tourReservation.getReservedTime())
                .build();
    }
}