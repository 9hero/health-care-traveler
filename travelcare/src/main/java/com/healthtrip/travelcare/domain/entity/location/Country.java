package com.healthtrip.travelcare.domain.entity.location;

import com.healthtrip.travelcare.domain.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Country extends BaseTimeEntity {

    @Builder
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
