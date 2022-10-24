package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.account.Tendency;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TourPackageTendency extends BaseTimeEntity {

    @Builder
    public TourPackageTendency(Long id, TourPackage tourPackage, Tendency tendency) {
        this.id = id;
        this.tourPackage = tourPackage;
        this.tendency = tendency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private TourPackage tourPackage;

    @ManyToOne
    @JoinColumn
    private Tendency tendency;
}
