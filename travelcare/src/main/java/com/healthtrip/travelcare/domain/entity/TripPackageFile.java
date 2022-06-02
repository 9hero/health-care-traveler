package com.healthtrip.travelcare.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TripPackageFile extends BaseTimeEntity{

    @Builder
    public TripPackageFile(Long id, TripPackage tripPackage, String url, String fileName, String originalName, long fileSize) {
        this.id = id;
        this.tripPackage = tripPackage;
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
    private TripPackage tripPackage;

    private String url;

    private String fileName;

    private String originalName;

    private long fileSize;


    public void setTripPackage(TripPackage tripPackage){
        this.tripPackage = tripPackage;
        tripPackage.getTripPackageFileList().add(this);
    }
}
