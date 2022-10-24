package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true,exclude = {"account","accountAddress"})
public class AccountCommon extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

//    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Account account;

    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Booker.Gender gender;
    private LocalDate birth;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    //cascade=ALL 할시 예약자가 빌려쓰던 주소가 같이 사라짐 정책에 따라 결정 1. 계정 삭제시 예약자 데이터도 삭제 2. 예약자 데이터는 남김
    private AccountAddress accountAddress;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    private Personality personality;

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    private String phone;
    private String emergencyContact;

    public static AccountCommon toEntityBasic(PersonData personData){
        return AccountCommon.builder()
//                .account(account)
//                .accountAddress(accountAddress)
                .gender(personData.getGender())
                .emergencyContact(personData.getEmergencyContact())
                .phone(personData.getPhone())
                .birth(personData.getBirth())
                .lastName(personData.getLastName())
                .firstName(personData.getFirstName())
                .build();
    }
    public void setRelation(AccountAddress accountAddress, Account account){
        this.accountAddress = accountAddress;
        this.account = account;
    }
}
