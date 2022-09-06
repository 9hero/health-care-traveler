package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class MedicalCheckupProgramRepoTest {

    @Autowired
    private MedicalCheckupProgramRepo medicalCheckupProgramRepo;
    private EntityProvider entityProvider;
    private MedicalCheckupProgram medicalCheckupProgram;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        medicalCheckupProgram = MedicalCheckupProgram.builder()
                .hospital(entityProvider.getHospital())
                .programName("임시 검진")

                .programType(MedicalCheckupProgram.ProgramType.Total)
                .priceForMan(BigDecimal.ONE)
                .priceForWoman(BigDecimal.TEN)
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        medicalCheckupProgramRepo.save(medicalCheckupProgram);
        // then
        assertThat(medicalCheckupProgram.getId()).isNotNull();
        medicalCheckupProgram.getProgramCategories().forEach(programCategories -> {
            assertThat(programCategories.getId()).isNotNull();
            assertThat(programCategories.getMedicalCheckupCategory().getId()).isNotNull();
        });
        assertThat(medicalCheckupProgram.getId()).isNotNull();

    }


}