package com.healthtrip.travelcare.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class ReservationPersonRepositoryTest {

    @Autowired
    ReservationPersonRepository reservationPersonRepository;

    @Test
//    @Transactional
    void findByReservationInfoId() {
//        reservationPersonRepository.findByReservationInfoId(32L).forEach(System.out::println);
        reservationPersonRepository.findByReservationId(22L,70L).forEach(System.out::println);
    }
}