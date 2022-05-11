package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.ReservationDate;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDateResponseDto {

    private Long id ;

    private LocalDateTime departAt;

    private LocalDateTime arriveAt;

    private short currentNumPeople;

    private short peopleLimit;

    public ReservationDateResponseDto entityToResponseBasic(ReservationDate reservationDate){
        return ReservationDateResponseDto.builder()
                .id(reservationDate.getId())
                .departAt(reservationDate.getDepartAt())
                .arriveAt(reservationDate.getArriveAt())
                .currentNumPeople(reservationDate.getCurrentNumPeople())
                .peopleLimit(reservationDate.getPeopleLimit())
                .build();
    }
}
