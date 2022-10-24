package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class TourReservationRepositoryTest {

    @Autowired
    TourReservationRepository tourReservationRepository;

    TourReservation tourReservation;
    @BeforeEach
    void setup() {
        EntityProvider entityProvider = new EntityProvider();
        tourReservation = entityProvider.getTourReservation();
    }


    @Test
    @DisplayName("저장: 성공")
    @Transactional
    void save() {
        // given

        // when
        TourReservation savedTourReservation  = tourReservationRepository.save(tourReservation);

        //then
        assertThat(savedTourReservation.getId()).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("Reservation LAZY 테스트 ")
    void lazyGet() {
        var a = tourReservationRepository.findById(82L).get();
        System.out.println(a.getAmount());
    }

}