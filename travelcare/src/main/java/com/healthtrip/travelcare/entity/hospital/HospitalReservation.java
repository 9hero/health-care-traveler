package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.reservation.AddedCheckup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class HospitalReservation extends BaseTimeEntity {

    @Builder
    public HospitalReservation(Long id, MedicalCheckupProgram medicalCheckupProgram, Short personCount, LocalDateTime reservedTime, BigDecimal amount) {
        this.id = id;
        this.medicalCheckupProgram = medicalCheckupProgram;
        this.personCount = personCount;
        this.reservedTime = reservedTime;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    private MedicalCheckupProgram medicalCheckupProgram;

    private Short personCount;
    private LocalDateTime reservedTime;

    private BigDecimal amount;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,mappedBy = "hospitalReservation")
    private List<AddedCheckup> medicalCheckupOptionalList;
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
