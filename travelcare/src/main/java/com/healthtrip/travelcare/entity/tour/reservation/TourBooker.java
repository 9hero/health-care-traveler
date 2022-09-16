package com.healthtrip.travelcare.entity.tour.reservation;


import com.healthtrip.travelcare.entity.BaseTimeEntity;
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
@Table(name = "reservation_person")
public class TourBooker extends BaseTimeEntity {
    @Builder
    public TourBooker(Long id, String lastName , TourReservation tourReservation, TourBookerAddress address, String firstName, PersonData.Gender gender, LocalDate birth, String phone, String emergencyContact) {
        this.id = id;
        this.tourReservation = tourReservation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.tourBookerAddress = address;
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
    @JoinColumn(name = "reservation_info_id")
    @ToString.Exclude
    private TourReservation tourReservation;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    @ToString.Exclude
    private TourBookerAddress tourBookerAddress;



    public static TourBooker reservationPersonBasicEntity(PersonData personData){
        return TourBooker.builder()
                .firstName(personData.getFirstName())
                .lastName(personData.getLastName())
                .gender(personData.getGender())
                .birth(personData.getBirth())
                .phone(personData.getPhone())
                .emergencyContact(personData.getEmergencyContact())
                .build();
    }

    public void relationSet(TourReservation tourReservation, TourBookerAddress address) {
        this.tourReservation = tourReservation;
        this.tourBookerAddress = address;
    }
}

