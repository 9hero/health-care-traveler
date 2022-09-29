package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
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
    public AddedCheckup(Long id, HospitalReservation hospitalReservation, MedicalCheckupOptional medicalCheckupOptional, String bookerName) {
        this.id = id;
        this.hospitalReservation = hospitalReservation;
        this.medicalCheckupOptional = medicalCheckupOptional;
        this.bookerName = bookerName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private HospitalReservation hospitalReservation;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MedicalCheckupOptional medicalCheckupOptional;
    private String bookerName;
}
