package com.healthtrip.travelcare.location;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.AccountAddress;
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
class AccountAddressRepositoryTest {


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
        AccountAddress accountAddressEntity = entityProvider.getAccountAddress();
        // when
        AccountAddress savedAddress = addressRepository.save(accountAddressEntity);
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
        AccountAddress accountAddressEntity = entityProvider.getAccountAddress();
        AccountAddress savedAddress = addressRepository.save(accountAddressEntity);
        Country savedCountry = savedAddress.getCountry();
        // when
        AccountAddress foundAddress = addressRepository.findById(savedAddress.getId()).get();

        //then
        assertThat(savedCountry).isNotNull();
        assertThat(savedCountry.getId()).isGreaterThan(0);
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getId()).isGreaterThan(0);
        assertThat(foundAddress.getId()).isEqualTo(savedAddress.getId());
    }

}