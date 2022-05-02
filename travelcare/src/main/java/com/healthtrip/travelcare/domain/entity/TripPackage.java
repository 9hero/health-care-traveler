package com.healthtrip.travelcare.domain.entity;

import javax.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class TripPackage extends BaseTimeEntity{

    @Builder
    public TripPackage(Long id, Account account, String description, BigDecimal price, String type, List<ReservationInfo> reservationInfoList) {
        this.id = id;
        this.account = account;
        this.description = description;
        this.price = price;
        this.type = type;
        this.reservationInfoList = reservationInfoList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id")
    private Account account;

    private String description;

    private BigDecimal price;

    private String type;

//    private Moneta //Moneta jsr354 국제 통화표현 라이브러리
    @OneToMany(mappedBy = "tripPackage")
    @ToString.Exclude
    private List<ReservationInfo> reservationInfoList;

    public void setAccount(Account account){
        this.account = account;
    }
}
