package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.ProgramCheckupItem;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class ProgramCheckupItemRepoTest {

    @Autowired
    private ProgramCheckupItemsRepo programCheckupItemsRepo;

    private ProgramCheckupItem programCheckupItem;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        programCheckupItem = ProgramCheckupItem.builder()
                .medicalCheckupItem(entityProvider.getMedicalCheckupItem())
                .medicalCheckupProgram(entityProvider.getMedicalCheckupProgram())
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        programCheckupItemsRepo.save(programCheckupItem);
        // then
        assertThat(programCheckupItem.getId()).isNotNull();
        assertThat(programCheckupItem.getMedicalCheckupItem().getId()).isNotNull();
        assertThat(programCheckupItem.getMedicalCheckupProgram().getId()).isNotNull();

    }
}