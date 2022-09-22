package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ReservationTourOptions {
    @Builder
    public ReservationTourOptions(Long id, Reservation reservation, TourOption tourOption, String day, Short manCount, String requesterName) {
        this.id = id;
        this.reservation = reservation;
        this.tourOption = tourOption;
        this.day = day;
        this.manCount = manCount;
        this.requesterName = requesterName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Reservation reservation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TourOption tourOption;

    private String day;

    private Short manCount;

    private String requesterName;
}
