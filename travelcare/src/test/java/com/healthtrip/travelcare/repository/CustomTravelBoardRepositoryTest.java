package com.healthtrip.travelcare.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class CustomTravelBoardRepositoryTest {

    @Autowired
    CustomTravelBoardRepository customTravelBoardRepository;

    @Test
    void findByReservationInfoId() {
        customTravelBoardRepository.findByReservationInfoId(23L).forEach(System.out::println);
    }
}