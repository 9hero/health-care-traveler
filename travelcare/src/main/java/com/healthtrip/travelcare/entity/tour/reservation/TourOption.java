package com.healthtrip.travelcare.entity.tour.reservation;

import com.healthtrip.travelcare.entity.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class TourOption {
    @Builder
    public TourOption(Long id, String optionName) {
        this.id = id;
        this.optionName = optionName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;
}
