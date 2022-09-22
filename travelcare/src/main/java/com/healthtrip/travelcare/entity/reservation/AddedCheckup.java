package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.hospital.Hospital;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupItem;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupOptional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation_added_checkup")
public class AddedCheckup {
    @Builder
    public AddedCheckup(Long id, HospitalReservation hospitalReservation, MedicalCheckupOptional medicalCheckupOptional, Booker booker) {
        this.id = id;
        this.hospitalReservation = hospitalReservation;
        this.medicalCheckupOptional = medicalCheckupOptional;
        this.booker = booker;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private HospitalReservation hospitalReservation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private MedicalCheckupOptional medicalCheckupOptional;

    @ManyToOne
    private Booker booker;
}
