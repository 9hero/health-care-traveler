package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TourPlaceList extends BaseTimeEntity {

    @Builder
    public TourPlaceList(Long id, TourItineraryElement tourItineraryElement, TourPlace tourPlace, ShowType showType) {
        this.id = id;
        this.tourItineraryElement = tourItineraryElement;
        this.tourPlace = tourPlace;
        this.showType = showType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    private TourItineraryElement tourItineraryElement;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    private TourPlace tourPlace;

    @Enumerated(EnumType.STRING)
    private ShowType showType;

    public enum ShowType{
        THREE,FULL
    }
}
