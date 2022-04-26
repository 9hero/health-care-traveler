package com.healthtrip.travelcare.domain.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ReservationInfo extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private TripPackage tripPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReservationDate reservationDate;

    private short personCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        Y,N,B
    }
}
