package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.Hospital;
import com.healthtrip.travelcare.entity.hospital.HospitalAddress;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class HospitalAddressRepoTest {
    @Autowired
    HospitalAddressRepo hospitalAddressRepo;
    EntityProvider entityProvider;
    HospitalAddress hospitalAddress;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        hospitalAddress = entityProvider.getHospitalAddress();
    }

    @Test
    void save() {
        // given
        Country country = entityProvider.getCountry();

        // when
        hospitalAddressRepo.save(hospitalAddress);
        // then
        assertThat(hospitalAddress.getId()).isNotNull();

    }
}