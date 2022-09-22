package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
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
public class Booker {

    @Builder
    public Booker(Long id, String firstName, String lastName, Gender gender, LocalDate birth, String phone, String emergencyContact, String simpleAddress, boolean tourReserved, boolean hospitalReserved, Reservation reservation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.simpleAddress = simpleAddress;
        this.tourReserved = tourReserved;
        this.hospitalReserved = hospitalReserved;
        this.reservation = reservation;
//        this.bookerAddress = bookerAddress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Booker.Gender gender;

    private LocalDate birth;
    private String phone;
    private String emergencyContact;
    private String simpleAddress;
    private boolean tourReserved;
    private boolean hospitalReserved;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    @ToString.Exclude
    private Reservation reservation;


//    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
//    @JoinColumn
//    @ToString.Exclude
//    private BookerAddress bookerAddress;

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

    public enum Gender {
        M,W
    }

}
