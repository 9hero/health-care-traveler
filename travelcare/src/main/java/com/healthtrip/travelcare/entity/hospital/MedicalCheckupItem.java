package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class MedicalCheckupItem extends BaseTimeEntity {

    @Builder
    public MedicalCheckupItem(Long id, String name, Set<MedicalCheckupItemCategory> medicalCheckupItemCategories) {
        this.id = id;
        this.name = name;
        this.medicalCheckupItemCategories = medicalCheckupItemCategories;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "medicalCheckupItem",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Set<MedicalCheckupItemCategory> medicalCheckupItemCategories;

    public void addCategory(MedicalCheckupItemCategory medicalCheckupItemCategory) {
        if (this.medicalCheckupItemCategories == null){
            this.medicalCheckupItemCategories = new HashSet<>();
        }
        this.medicalCheckupItemCategories.add(medicalCheckupItemCategory);
        medicalCheckupItemCategory.setMedicalCheckupItem(this);
    }
}
