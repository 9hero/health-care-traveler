package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.reservation.AddedCheckup;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class AddedCheckupRepositoryTest {

    @Autowired
    private AddedCheckupRepository addedCheckupRepository;

    private AddedCheckup addedCheckup;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        addedCheckup = AddedCheckup.builder()
//                .booker(entityProvider.getTourBooker())
                .medicalCheckupOptional(entityProvider.getMedicalCheckupOptional())
                .hospitalReservation(entityProvider.getHospitalReservation())
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        addedCheckupRepository.save(addedCheckup);
        // then
        assertThat(addedCheckup.getId()).isNotNull();
        assertThat(addedCheckup.getMedicalCheckupOptional().getId()).isNotNull();
        assertThat(addedCheckup.getHospitalReservation().getId()).isNotNull();
//        assertThat(addedCheckup.getBooker().getId()).isNotNull();
    }
}