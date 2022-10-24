package com.healthtrip.travelcare.entity.tour.reservation;

import javax.persistence.*;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class TourReservation extends BaseTimeEntity {

    @Builder
    public TourReservation(Long id, short personCount, List<ReservationTourOptions> reservationTourOptions, BigDecimal amount, LocalDateTime reservedTime, TourPackage tourPackage) {
        this.id = id;
        this.personCount = personCount;
        this.reservationTourOptions = reservationTourOptions;
        this.amount = amount;
        this.reservedTime = reservedTime;
        this.tourPackage = tourPackage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private short personCount;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,mappedBy = "tourReservation")
    @BatchSize(size = 10)
//    @ToString.Exclude
    private List<ReservationTourOptions> reservationTourOptions;

    // 예약 금액
    private BigDecimal amount;

    private LocalDateTime reservedTime;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private TourPackage tourPackage;

    public void addTourOptionAmount(BigDecimal price) {
        this.amount = this.amount.add(price);
    }

}
