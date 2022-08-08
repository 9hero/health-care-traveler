package com.healthtrip.travelcare.entity;

import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.repository.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.TourPackageRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Disabled
class TourPackageDateTest {


    @Autowired
    TourPackageDateRepository repository;
    @Autowired
    TourPackageRepository tourPackageRepository;

    @Test
    @Transactional
    @Rollback(value = true)
    void saveAndFind(){
         TourPackageDate tourPackageDate = TourPackageDate.builder()
                .tourPackage(tourPackageRepository.findAll().get(0))
                .departAt(LocalDateTime.of(2022,5,15,4,30))
                .arriveAt(LocalDateTime.of(2022,5,15,4,30).plusDays(1L))
                .peopleLimit((short) 25)
                .currentNumPeople((short)10)
                .build();
         repository.save(tourPackageDate);
         repository.flush();

         repository.findAll().forEach(System.out::println);

    }
}