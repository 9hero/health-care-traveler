package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupCategory;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class MedicalCheckupCategoryRepoTest {

    @Autowired
    private MedicalCheckupCategoryRepo medicalCheckupCategoryRepo;

    private MedicalCheckupCategory medicalCheckupCategory;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        medicalCheckupCategory = MedicalCheckupCategory.builder()
                .name("기본 검사")
                .build();
    }

    @Test
    @DisplayName("저장")
    void saveAll(){
        // given
        MedicalCheckupCategory bloodCheckup = MedicalCheckupCategory.builder()
                .name("혈액/종합 검사")
                .build();
        List<MedicalCheckupCategory> categories = List.of(bloodCheckup, medicalCheckupCategory);
        // when
        medicalCheckupCategoryRepo.saveAll(categories);
        var categoryList=medicalCheckupCategoryRepo.findAll();
        // then
        assertThat(categoryList).isNotEmpty();
        assertThat(categoryList.size()).isEqualTo(2);
    }
}