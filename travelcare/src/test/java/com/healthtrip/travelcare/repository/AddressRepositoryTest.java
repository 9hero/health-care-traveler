package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.location.Address;
import com.healthtrip.travelcare.entity.location.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaUnitTest
class AddressRepositoryTest {


    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CountryRepository countryRepository;

    private Country country;
    private Address address;

    @BeforeEach
    void setUp() {
        country = Country.builder()
                .name("USA")
                .build();
        address = Address.builder()
                .country(country)
                .city("new york")
                .district("somewhere")
                .address("anywhere")
                .addressDetail("")
                .postalCode("12345")
                .build();
    }

    @Test
    @DisplayName("주소 저장 테스트")
    @Transactional
    void address_save_savedAddress() {
        // given
        Country savedCountry = countryRepository.save(country);

        // when
        Address savedAddress = addressRepository.save(address);

        //then
        assertThat(savedCountry).isNotNull();
        assertThat(savedCountry.getId()).isGreaterThan(0);
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }
    @Test
    @DisplayName("주소 조회 테스트")
//    @Transactional
    void address_save_savedAddress2() {
        // given
        Country savedCountry = countryRepository.save(country);
        Address savedAddress = addressRepository.save(address);

        // when
        Address foundAddress = addressRepository.findById(savedAddress.getId()).get();

        //then
        assertThat(savedCountry).isNotNull();
        assertThat(savedCountry.getId()).isGreaterThan(0);
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getId()).isGreaterThan(0);
        assertThat(foundAddress.getId()).isEqualTo(savedAddress.getId());
    }

}