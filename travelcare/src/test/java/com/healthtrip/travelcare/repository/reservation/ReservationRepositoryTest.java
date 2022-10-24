package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

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
        reservation.idGenerate();
        reservationRepository.save(reservation);
//        reservationRepository.flush();
        // then
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getAccount().getId()).isNotNull();
        assertThat(reservation.getTourReservation().getId()).isNotNull();
        assertThat(reservation.getHospitalReservation().getId()).isNotNull();
        assertThat(reservation.getTitle()).isNotEmpty();
    }

    @Test
    @DisplayName("findMyReservations")
    void findMyReservations(){
        // given
        reservationRepository.save(reservation);
        Long accountId = reservation.getAccount().getId();
        // when
        var a = reservationRepository.findMyReservations(accountId);
        // then
        assertThat(a).isNotEmpty();
        a.forEach(reservation1 -> assertThat(reservation1
        ).isNotNull());

    }

    @Test
    @DisplayName("findMyReservationInfo")
    void findMyReservationInfo(){
        // given
        var a =reservationRepository.findMyReservationInfo("220923R4921",261L);
        // when
        System.out.println("break");
        // then
    }

    @Test
    @DisplayName("옵션 불러오기 테스트")
    void reservationAndTourOption() {
        var a = reservationRepository.findByAddedTourOptionId(40L);
        a.getTourReservation().getReservationTourOptions();
    }
}