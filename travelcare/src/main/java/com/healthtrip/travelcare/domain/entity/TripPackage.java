package com.healthtrip.travelcare.domain.entity;

import javax.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TripPackage extends BaseTimeEntity{

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

    private short peopleLimit;
//    private Moneta //Moneta jsr354 국제 통화표현 라이브러리
    @OneToMany(mappedBy = "tripPackage")
    @ToString.Exclude
    private List<ReservationInfo> reservationInfoList;


}
