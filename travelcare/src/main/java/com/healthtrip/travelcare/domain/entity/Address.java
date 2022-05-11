package com.healthtrip.travelcare.domain.entity;

import lombok.*;

import javax.persistence.*;



@Getter
@NoArgsConstructor
@Entity
public class Address extends BaseTimeEntity{

    @Builder
    public Address(Long id, String address, String addressDetail, String district, String city, Country country, String postalCode) {
        this.id = id;
        this.address = address;
        this.addressDetail = addressDetail;
        this.district = district;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    private String postalCode;
}
