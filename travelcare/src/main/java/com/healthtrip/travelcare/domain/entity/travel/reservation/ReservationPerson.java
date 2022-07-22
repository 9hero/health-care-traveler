package com.healthtrip.travelcare.domain.entity.travel.reservation;


import com.healthtrip.travelcare.domain.entity.location.Address;
import com.healthtrip.travelcare.domain.entity.BaseTimeEntity;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class ReservationPerson extends BaseTimeEntity {
    @Builder
    public ReservationPerson(Long id, String lastName , ReservationInfo reservationInfo, Address address, String firstName, PersonData.Gender gender, LocalDate birth, String phone, String emergencyContact) {
        this.id = id;
        this.reservationInfo = reservationInfo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private PersonData.Gender gender;

    private LocalDate birth;
    private String phone;
    private String emergencyContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @ToString.Exclude
    private ReservationInfo reservationInfo;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    @ToString.Exclude
    private Address address;



    public static ReservationPerson reservationPersonBasicEntity(PersonData personData){
        return ReservationPerson.builder()
                .firstName(personData.getFirstName())
                .lastName(personData.getLastName())
                .gender(personData.getGender())
                .birth(personData.getBirth())
                .phone(personData.getPhone())
                .emergencyContact(personData.getEmergencyContact())
                .build();
    }

    public void relationSet(ReservationInfo reservationInfo,Address address) {
        this.reservationInfo = reservationInfo;
        this.address = address;
    }
}

