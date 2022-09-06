package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.Hospital;
import com.healthtrip.travelcare.entity.hospital.HospitalAddress;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class HospitalRepositoryTest {

    @Autowired
    HospitalRepository hospitalRepo;
    EntityProvider entityProvider;
    HospitalAddress hospitalAddress;
    Hospital hospital;
    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        hospitalAddress = entityProvider.getHospitalAddress();
        hospital = Hospital.builder()
                .hospitalAddress(hospitalAddress)
                .name("병원 이름")
                .description("병원 설명")
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        hospitalRepo.save(hospital);
        // then
        assertThat(hospital.getId()).isNotNull();
        assertThat(hospital.getHospitalAddress().getId()).isNotNull();
    }
}