package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupCategory extends BaseTimeEntity {
    // 검강검진 범주

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "medicalCheckupCategory",cascade = CascadeType.PERSIST)
    private List<ProgramCategory> programCategories;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "medicalCheckupCategory",cascade = CascadeType.PERSIST)
    private List<MedicalCheckupItemCategory> medicalCheckupItemCategories;


}
