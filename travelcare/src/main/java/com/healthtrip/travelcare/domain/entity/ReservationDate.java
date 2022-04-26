package com.healthtrip.travelcare.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ReservationDate extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private TripPackage tripPackage;

    private LocalDateTime departAt;

    private LocalDateTime arriveAt;

    private short currentNumPeople;
}
