package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupOptional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation_added_checkup")
public class AddedCheckup extends BaseTimeEntity {
    @Builder
    public AddedCheckup(Long id, String bookerName, BigDecimal amount, HospitalReservation hospitalReservation, MedicalCheckupOptional medicalCheckupOptional) {
        this.id = id;
        this.bookerName = bookerName;
        this.amount = amount;
        this.hospitalReservation = hospitalReservation;
        this.medicalCheckupOptional = medicalCheckupOptional;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookerName;
    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    private HospitalReservation hospitalReservation;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    private MedicalCheckupOptional medicalCheckupOptional;

//    @OneToMany(mappedBy = "addedCheckup",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
//    private List<AddedCheckupBooker> bookers;
}
