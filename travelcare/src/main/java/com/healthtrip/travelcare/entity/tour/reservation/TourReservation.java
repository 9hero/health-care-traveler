package com.healthtrip.travelcare.entity.tour.reservation;

import javax.persistence.*;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicUpdate
@ToString(callSuper = true)
public class TourReservation extends BaseTimeEntity{

    @Builder
    public TourReservation(Long id, short personCount, List<CustomTravelBoard> customTravelBoard, BigDecimal amount, LocalDateTime reservedTime, TourPackage tourPackage) {
        this.id = id;
        this.personCount = personCount;
        this.customTravelBoard = customTravelBoard;
        this.amount = amount;
        this.reservedTime = reservedTime;
        this.tourPackage = tourPackage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private short personCount;

    @ToString.Exclude
    @OneToMany(mappedBy = "tourReservation",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<CustomTravelBoard> customTravelBoard;

    // 예약 금액
    private BigDecimal amount;

    private LocalDateTime reservedTime;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private TourPackage tourPackage;
}
