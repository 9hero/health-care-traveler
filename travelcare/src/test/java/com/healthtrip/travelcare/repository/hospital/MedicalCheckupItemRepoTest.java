package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupItem;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class MedicalCheckupItemRepoTest {

    @Autowired
    private MedicalCheckupItemRepo medicalCheckupItemRepo;

    private MedicalCheckupItem medicalCheckupItem;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        medicalCheckupItem = entityProvider.getMedicalCheckupItem();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        medicalCheckupItem = MedicalCheckupItem.builder()
                .medicalCheckupCategory(entityProvider.getMedicalCheckupCategory())
                .name("대소변 검사")
                .build();

        // when
        medicalCheckupItemRepo.save(medicalCheckupItem);

        // then
        assertThat(medicalCheckupItem.getId()).isNotNull();
        assertThat(medicalCheckupItem.getMedicalCheckupCategory().getId()).isNotNull();
    }
}