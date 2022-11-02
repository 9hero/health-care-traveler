package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationTourOptionsRequest {

    private Long tourOptionId;
    private String day;
    private Short manCount;
    private String requesterName;

    public ReservationTourOptions toReservationTourOptions() {
        return ReservationTourOptions.builder()
                .day(day)
                .manCount(manCount)
                .requesterName(requesterName)
                .build();
    }
}
