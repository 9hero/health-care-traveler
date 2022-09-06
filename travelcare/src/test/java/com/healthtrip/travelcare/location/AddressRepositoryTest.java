package com.healthtrip.travelcare.location;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.location.Address;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.repository.location.AddressRepository;
import com.healthtrip.travelcare.test_common.EntityProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaUnitTest
class AddressRepositoryTest {


    @Autowired
    AddressRepository addressRepository;

    private EntityProvider entityProvider;

    @BeforeEach
    void setUp() {
        entityProvider = new EntityProvider();
    }

    @Test
    @DisplayName("주소 저장 테스트 with EntityProvider")
    @Transactional
    void address_save_savedAddress_withEntityProvider() {
        // given
        Address addressEntity = entityProvider.getAddress();
        // when
        Address savedAddress = addressRepository.save(addressEntity);
        Country savedCountry = savedAddress.getCountry();

        //then
        System.out.println("country id : "+savedCountry.getId());
        assertThat(savedCountry).isNotNull();
        assertThat(savedCountry.getId()).isGreaterThan(0);
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }
    @Test
    @DisplayName("주소 조회 테스트")
    void address_save_savedAddress2() {
        // given
        Address addressEntity = entityProvider.getAddress();
        Address savedAddress = addressRepository.save(addressEntity);
        Country savedCountry = savedAddress.getCountry();
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