package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.tour.reservation.TourBookerAddress;
import com.healthtrip.travelcare.repository.dto.request.AddressRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BookerAddress {
    @Builder
    public BookerAddress(Long id, String address, String addressDetail, String district, String city, Country country, String postalCode) {
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
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Country country;

    private String postalCode;

    public static BookerAddress toEntityBasic(AddressRequest addressData){
        return BookerAddress.builder()
                .address(addressData.getAddress1())
                .addressDetail(addressData.getAddress2())
                .district(addressData.getDistrict())
                .city(addressData.getCityName())
                .postalCode(addressData.getPostalCode())
                .build();
    }
    public void setCountry(Country country) {
        this.country = country;
    }

}
