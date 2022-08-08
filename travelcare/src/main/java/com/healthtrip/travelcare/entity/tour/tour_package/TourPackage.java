package com.healthtrip.travelcare.entity.tour.tour_package;

import javax.persistence.*;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "trip_package")
public class TourPackage extends BaseTimeEntity {

    @Builder
    public TourPackage(Long id, Account account, String title, String description, BigDecimal price, String type, List<TourPackageFile> tourPackageFileList) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.tourPackageFileList = tourPackageFileList;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id")
    private Account account;

    private String title;

    private String description;

    private BigDecimal price;

    private String type;

//    private Moneta //Moneta jsr354 국제 통화표현 라이브러리
    @OneToMany(mappedBy = "tourPackage",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<TourPackageFile> tourPackageFileList;

    @OneToMany(mappedBy = "tourPackage")
    private List<TourPackageDate> tourPackageDateList;

    public void setAccount(Account account){
        this.account = account;
    }
}
