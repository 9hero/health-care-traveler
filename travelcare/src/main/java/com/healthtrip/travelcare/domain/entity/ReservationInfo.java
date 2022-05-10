package com.healthtrip.travelcare.domain.entity;

import javax.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor
@Entity
public class ReservationInfo extends BaseTimeEntity{

    @Builder
    public ReservationInfo(Long id, Account account, ReservationDate reservationDate, short personCount, Status status) {
        this.id = id;
        this.account = account;

        this.reservationDate = reservationDate;
        this.personCount = personCount;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_id")
    @ToString.Exclude
    private ReservationDate reservationDate;

    private short personCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        Y,N,B
    }
}
