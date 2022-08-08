package com.healthtrip.travelcare.entity.location;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
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
