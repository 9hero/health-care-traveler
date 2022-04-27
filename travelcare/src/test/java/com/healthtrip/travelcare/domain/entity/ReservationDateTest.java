package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.ReservationDateRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationDateTest {


    @Autowired
    ReservationDateRepository repository;
    @Autowired
    TripPackageRepository tripPackageRepository;

    @Test
    @Transactional
    @Rollback
    void saveAndFind(){
         ReservationDate reservationDate = ReservationDate.builder()
                .currentNumPeople((short)10)
                .tripPackage(tripPackageRepository.findAll().get(0))
                .departAt(LocalDateTime.of(2022,5,15,4,30))
                .arriveAt(LocalDateTime.of(2022,5,15,4,30).plusDays(1L))
                .build();
         repository.save(reservationDate);
         repository.flush();

         repository.findAll().forEach(System.out::println);

    }
}