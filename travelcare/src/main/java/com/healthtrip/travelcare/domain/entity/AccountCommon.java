package com.healthtrip.travelcare.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true,exclude = {"account","address"})
public class AccountCommon extends BaseTimeEntity{

    @Id
    @Column(name = "user_id")
    private Long userId;

//    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private Account account;

    private String firstName;
    private String lastName;
    private char gender;
    private LocalDate birth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    //cascade=ALL 할시 예약자가 빌려쓰던 주소가 같이 사라짐 정책에 따라 결정 1. 계정 삭제시 예약자 데이터도 삭제 2. 예약자 데이터는 남김
    private Address address;

    private String phone;
    private String emergencyContact;
}
