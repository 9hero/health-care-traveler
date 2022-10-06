package com.healthtrip.travelcare.entity.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReservationRejection {

    @Builder
    public ReservationRejection(Long id, Reservation reservation, String reason) {
        this.id = id;
        this.reservation = reservation;
        this.reason = reason;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @Setter
    private Reservation reservation;

    private String reason;
}
