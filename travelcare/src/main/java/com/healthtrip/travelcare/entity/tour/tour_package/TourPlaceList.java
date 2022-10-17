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
    public TourPlaceList(Long id, TourItineraryElement tourItineraryElement, TourPlace tourPlace, PlaceShowType placeShowType) {
        this.id = id;
        this.tourItineraryElement = tourItineraryElement;
        this.tourPlace = tourPlace;
        this.placeShowType = placeShowType;
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
    @Column(name = "show_type")
    private PlaceShowType placeShowType;

    public enum PlaceShowType {
        THREE,FULL
    }
}
