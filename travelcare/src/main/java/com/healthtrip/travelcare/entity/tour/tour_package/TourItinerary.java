package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class TourItinerary extends BaseTimeEntity {

    @Builder
    public TourItinerary(Long id, String day, String location, String specificLocations, String accommodation, String details, String notice, TourPackage tourPackage, Set<TourItineraryElement> itineraryElements) {
        this.id = id;
        this.day = day;
        this.location = location;
        this.specificLocations = specificLocations;
        this.accommodation = accommodation;
        this.details = details;
        this.notice = notice;
        this.tourPackage = tourPackage;
        this.itineraryElements = itineraryElements;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일차
    private String day;
    // 지역
    private String location;
    // 지역상세
    private String specificLocations;

    // 숙소(테이블 분리 가능)
    private String accommodation;

    // 세부 일정
    private String details;

    // 일정 유의사항
    private String notice;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
    private TourPackage tourPackage;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "tourItinerary",fetch = FetchType.LAZY)
    private Set<TourItineraryElement> itineraryElements;


}
