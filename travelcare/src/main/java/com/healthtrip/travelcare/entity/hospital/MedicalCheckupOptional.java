package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupOptional extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MedicalCheckupItem medicalCheckupItem;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Hospital hospital;

    private BigDecimal priceForMan;

    private BigDecimal priceForWoman;
}
