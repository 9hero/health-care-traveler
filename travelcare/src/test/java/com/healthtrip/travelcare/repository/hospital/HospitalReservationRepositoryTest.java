package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class HospitalReservationRepositoryTest {

    @Autowired
    private HospitalReservationRepository hospitalReservationRepository;

    private HospitalReservation hospitalReservation;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        hospitalReservation = entityProvider.getHospitalReservation();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        // when
        hospitalReservationRepository.save(hospitalReservation);

        // then
        assertThat(hospitalReservation.getId()).isNotNull();
    }
}