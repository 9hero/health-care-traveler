package com.healthtrip.travelcare.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
class TourReservationPersonRepositoryTest {

    @Autowired
    TourReservationPersonRepository tourReservationPersonRepository;

    @Test
//    @Transactional
    void findByReservationInfoId() {
//        reservationPersonRepository.findByReservationInfoId(32L).forEach(System.out::println);
        tourReservationPersonRepository.findByTourReservationId(22L,70L).forEach(System.out::println);
    }
}