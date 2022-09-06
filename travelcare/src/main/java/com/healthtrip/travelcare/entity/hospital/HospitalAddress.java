package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.location.Country;
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
public class HospitalAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_1")
    private String address;

    @Column(name = "address_2")
    private String addressDetail;

    private String district;

    @Column(name = "city_name")
    private String city;

    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Country country;

    private String postalCode;

}
