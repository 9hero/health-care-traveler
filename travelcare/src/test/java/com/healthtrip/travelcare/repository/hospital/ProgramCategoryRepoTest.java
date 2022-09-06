package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupCategory;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import com.healthtrip.travelcare.entity.hospital.ProgramCategory;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class ProgramCategoryRepoTest {

    @Autowired
    private ProgramCheckupCategoriesRepo programCheckupCategoriesRepo;

    private ProgramCategory programCategory;

    private EntityProvider entityProvider;
    private MedicalCheckupCategory medicalCheckupCategory;
    private MedicalCheckupProgram medicalCheckupProgram;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        medicalCheckupCategory = entityProvider.getMedicalCheckupCategory();
        medicalCheckupProgram = entityProvider.getMedicalCheckupProgram();
        programCategory = ProgramCategory.builder()
                .medicalCheckupProgram(medicalCheckupProgram)
                .medicalCheckupCategory(medicalCheckupCategory)
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        medicalCheckupCategory = entityProvider.getMedicalCheckupCategory();
        medicalCheckupProgram = entityProvider.getMedicalCheckupProgram();
        programCategory = ProgramCategory.builder()
                .medicalCheckupProgram(medicalCheckupProgram)
                .medicalCheckupCategory(medicalCheckupCategory)
                .build();
        // when
        var categoriesOfProgram = programCheckupCategoriesRepo.save(programCategory);

        // then
        // 중간 테이블
        assertThat(categoriesOfProgram).isNotNull();
        assertThat(categoriesOfProgram.getId()).isNotNull();

        // 연관 관계
        assertThat(categoriesOfProgram.getMedicalCheckupCategory().getId()).isNotNull();
        assertThat(categoriesOfProgram.getMedicalCheckupProgram().getId()).isNotNull();

    }
}