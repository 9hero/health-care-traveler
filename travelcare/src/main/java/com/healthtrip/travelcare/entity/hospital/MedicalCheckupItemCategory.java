package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MedicalCheckupItemCategory extends BaseTimeEntity {

    @Builder
    public MedicalCheckupItemCategory(Long id, MedicalCheckupItem medicalCheckupItem, MedicalCheckupCategory medicalCheckupCategory) {
        this.id = id;
        this.medicalCheckupItem = medicalCheckupItem;
        this.medicalCheckupCategory = medicalCheckupCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @Setter
    @ManyToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
    private MedicalCheckupItem medicalCheckupItem;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
    private MedicalCheckupCategory medicalCheckupCategory;

}
