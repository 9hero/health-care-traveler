package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class TourPlaceImage extends BaseTimeEntity {

    @Builder
    public TourPlaceImage(Long id, TourPlace tourPlace, String url, String fileName, String originalName, long fileSize) {
        this.id = id;
        this.tourPlace = tourPlace;
        this.url = url;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileSize = fileSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private TourPlace tourPlace;

    private String url;

    private String fileName;

    private String originalName;

    private long fileSize;

}
