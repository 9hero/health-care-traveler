package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramCategory extends BaseTimeEntity {
    // 건강검진 프로그램과 범주 중간 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MedicalCheckupProgram medicalCheckupProgram;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MedicalCheckupCategory medicalCheckupCategory;

    public void setMedicalCheckupProgram(MedicalCheckupProgram medicalCheckupProgram) {
        this.medicalCheckupProgram = medicalCheckupProgram;
        medicalCheckupProgram.getProgramCategories().add(this);
    }

}
