package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class HospitalReservation extends BaseTimeEntity {

    @Builder
    public HospitalReservation(Long id, MedicalCheckupProgram medicalCheckupProgram, Short manCount, LocalDateTime reservedTime, BigDecimal amount) {
        this.id = id;
        this.medicalCheckupProgram = medicalCheckupProgram;
        this.manCount = manCount;
        this.reservedTime = reservedTime;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    private MedicalCheckupProgram medicalCheckupProgram;

    private Short manCount;
    private LocalDateTime reservedTime;
    private BigDecimal amount;
}
