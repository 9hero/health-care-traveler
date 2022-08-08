package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "trip_package_file")
public class TourPackageFile extends BaseTimeEntity {

    @Builder
    public TourPackageFile(Long id, TourPackage tourPackage, String url, String fileName, String originalName, long fileSize) {
        this.id = id;
        this.tourPackage = tourPackage;
        this.url = url;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileSize = fileSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_package_id")
    private TourPackage tourPackage;

    private String url;

    private String fileName;

    private String originalName;

    private long fileSize;


    public void setTourPackage(TourPackage tourPackage){
        this.tourPackage = tourPackage;
        tourPackage.getTourPackageFileList().add(this);
    }
}
