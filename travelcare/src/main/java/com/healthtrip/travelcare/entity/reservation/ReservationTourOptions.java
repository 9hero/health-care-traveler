package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class ReservationTourOptions extends BaseTimeEntity {
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

    @ToString.Exclude
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Reservation reservation;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @ToString.Exclude
    @JoinColumn
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private TourOption tourOption;

    public void setTourOption(TourOption tourOption) {
        this.tourOption = tourOption;
    }

    private String day;

    private Short manCount;

    private String requesterName;
}
