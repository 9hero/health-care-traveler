package com.healthtrip.travelcare.entity.tour.tour_package;

import javax.persistence.*;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "tour_package")
public class TourPackage extends BaseTimeEntity {

    @Builder
    public TourPackage(Long id, Account account, String title, String description, String standardOffer, String nonOffer, String notice, TourPackagePrices prices, TourPackageFile mainImage, List<TourPackageFile> tourPackageFileList, List<TourItinerary> tourItineraryList, List<TourPackageTendency> tendencyList) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.description = description;
        this.standardOffer = standardOffer;
        this.nonOffer = nonOffer;
        this.notice = notice;
        this.prices = prices;
        this.mainImage = mainImage;
        this.tourPackageFileList = tourPackageFileList;
        this.tourItineraryList = tourItineraryList;
        this.tendencyList = tendencyList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id")
    private Account account;

    private String title;

    private String description;

//  이 아래 4개의 필드를 불러오지 않고는 영속성을 사용할 수 없다면,
    // 1:1 맵핑을 해야한다. 항상 필요한 컬럼이 아니기 떄문이다.
    private String standardOffer;

    private String nonOffer;

    private String notice;

    @Embedded
    private TourPackagePrices prices;

    @Setter
    @ToString.Exclude
    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private TourPackageFile mainImage;

    @Setter
    @ToString.Exclude
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "tourPackage",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<TourPackageFile> tourPackageFileList;

    @ToString.Exclude
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "tourPackage",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<TourItinerary> tourItineraryList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "tourPackage",cascade = CascadeType.PERSIST)
    private List<TourPackageTendency> tendencyList;

    public void setAccount(Account account){
        this.account = account;
    }


    public void addTendency(TourPackageTendency tendency) {
        if (tendencyList == null) {
            tendencyList = new ArrayList<>();
        }
        tendencyList.add(tendency);
    }
}
