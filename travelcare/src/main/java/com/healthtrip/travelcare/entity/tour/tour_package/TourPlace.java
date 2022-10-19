package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.repository.dto.request.TourPlaceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TourPlace extends BaseTimeEntity {

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
    private List<TourPlaceImage> tourPlaceImages = new ArrayList<>();


    public void addTourPlaceImage(TourPlaceImage tourPlaceImage) {
        if (tourPlaceImages == null) {
            tourPlaceImages = new ArrayList<>();
        }
        tourPlaceImages.add(tourPlaceImage);
        tourPlaceImage.setTourPlace(this);
    }

    public void update(TourPlaceRequest tourPlaceRequest) {
        this.placeName = tourPlaceRequest.getPlaceName();
        this.summery = tourPlaceRequest.getSummery();
        this.description = tourPlaceRequest.getDescription();
    }
}
