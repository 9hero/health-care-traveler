package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class AccountCommon extends BaseTimeEntity {

    @Builder
    public AccountCommon(Long userId, Account account, String firstName, String lastName, Booker.Gender gender, LocalDate birth, AccountAddress accountAddress, Personality personality, String phone, String emergencyContact) {
        this.userId = userId;
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.accountAddress = accountAddress;
        this.personality = personality;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
    }

    @Id
    @Column(name = "user_id")
    private Long userId;

//    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Account account;

    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Booker.Gender gender;
    private LocalDate birth;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    //cascade=ALL 할시 예약자가 빌려쓰던 주소가 같이 사라짐 정책에 따라 결정 1. 계정 삭제시 예약자 데이터도 삭제 2. 예약자 데이터는 남김
    private AccountAddress accountAddress;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    @ToString.Exclude
    private Personality personality;

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    private String phone;
    private String emergencyContact;

    public void setAccountAddress(AccountAddress accountAddress) {
        this.accountAddress = accountAddress;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

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
