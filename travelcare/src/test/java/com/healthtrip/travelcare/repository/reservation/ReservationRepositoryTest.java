package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation reservation;


    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        reservation = entityProvider.getReservation();
    }
    @Test
    @DisplayName("저장")
    void save(){
        // given
//        reservation.idGenerate();
        // when
        reservationRepository.save(reservation);
        // then
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getAccount().getId()).isNotNull();
        assertThat(reservation.getTourReservation().getId()).isNotNull();
        assertThat(reservation.getHospitalReservation().getId()).isNotNull();
    }
}