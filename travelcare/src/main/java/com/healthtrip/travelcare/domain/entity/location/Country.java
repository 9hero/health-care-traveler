package com.healthtrip.travelcare.domain.entity.location;

import com.healthtrip.travelcare.domain.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



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

    private String name;
}
