package com.healthtrip.travelcare.entity.tour.tour_package;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TourPlace {

    @Builder
    public TourPlace(Long id, String placeName, String summery, String description, List<TourPlaceImage> tourPlaceImages) {
        this.id = id;
        this.placeName = placeName;
        this.summery = summery;
        this.description = description;
        this.tourPlaceImages = tourPlaceImages;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;

    private String summery;
    private String description;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "tourPlace",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private List<TourPlaceImage> tourPlaceImages;

}
