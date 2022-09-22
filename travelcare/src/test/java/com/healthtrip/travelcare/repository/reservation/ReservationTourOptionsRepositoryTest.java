package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class ReservationTourOptionsRepositoryTest {

    @Autowired
    private ReservationTourOptionsRepository reservationTourOptionsRepository;

    private ReservationTourOptions reservationTourOptions;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
         reservationTourOptions = ReservationTourOptions.builder()
                 .day("1일차")
                 .tourOption(entityProvider.getTourOption())
                 .manCount((short) 2)
                 .requesterName("김사장,한사장,돌돌이")
                 .reservation(entityProvider.getReservation())
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        reservationTourOptionsRepository.save(reservationTourOptions);
        // then
        assertThat(reservationTourOptions.getId()).isNotNull();
        assertThat(reservationTourOptions.getTourOption().getId()).isNotNull();
    }
}