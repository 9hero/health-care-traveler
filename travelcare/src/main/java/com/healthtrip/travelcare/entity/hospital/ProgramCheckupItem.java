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
public class ProgramCheckupItem extends BaseTimeEntity { // 엔티티명 다시 고민
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MedicalCheckupProgram medicalCheckupProgram;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MedicalCheckupItem medicalCheckupItem;
}
