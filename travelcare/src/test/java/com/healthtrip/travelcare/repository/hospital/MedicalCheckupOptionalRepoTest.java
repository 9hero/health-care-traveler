package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupOptional;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class MedicalCheckupOptionalRepoTest {

    @Autowired
    private MedicalCheckupOptionalRepo medicalCheckupOptionalRepo;

    private MedicalCheckupOptional medicalCheckupOptional;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        medicalCheckupOptional = entityProvider.getMedicalCheckupOptional();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        medicalCheckupOptionalRepo.save(medicalCheckupOptional);
        // then
        assertThat(medicalCheckupOptional.getId()).isNotNull();
        assertThat(medicalCheckupOptional.getHospital().getId()).isNotNull();
        assertThat(medicalCheckupOptional.getMedicalCheckupItem().getId()).isNotNull();

    }


}