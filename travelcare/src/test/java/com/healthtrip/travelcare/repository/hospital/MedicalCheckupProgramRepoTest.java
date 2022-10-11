package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class MedicalCheckupProgramRepoTest {

    @Autowired
    private MedicalCheckupProgramRepo medicalCheckupProgramRepo;
    private EntityProvider entityProvider;
    private MedicalCheckupProgram medicalCheckupProgram;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        medicalCheckupProgram = entityProvider.getMedicalCheckupProgram();
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
    @Test
    @DisplayName("findByHospital")
    void findByHospital(){
        // given
        medicalCheckupProgramRepo.save(medicalCheckupProgram);
        // when
        var foundProgram = medicalCheckupProgramRepo
                .findByHospitalId(entityProvider.getHospital().getId(),
                        PageRequest.of(0,10));

        // then
        assertThat(foundProgram).isNotEmpty();
    }
    @Test
    @DisplayName("findByCategoryIds")
    void findByCategoryIds(){
        // given
        medicalCheckupProgramRepo.save(medicalCheckupProgram);
        var ids = List.of(entityProvider.getMedicalCheckupCategory().getId());
        // when
        var foundProgramByCategory = medicalCheckupProgramRepo
                .findByCategoryIds(
                        ids, (long) ids.size()
                );
        // then
        assertThat(foundProgramByCategory).isNotEmpty();
    }

}