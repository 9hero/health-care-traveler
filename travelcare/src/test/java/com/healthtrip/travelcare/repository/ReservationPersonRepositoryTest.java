package com.healthtrip.travelcare.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class ReservationPersonRepositoryTest {

    @Autowired
    ReservationPersonRepository reservationPersonRepository;

    @Test
    void findByReservationInfoId() {
        reservationPersonRepository.findByReservationInfoId(32L).forEach(System.out::println);
    }
}