package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hospital extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private HospitalAddress hospitalAddress;

    private String name;
    private String description;
    private String simpleAddress;
}
