package com.healthtrip.travelcare.entity;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.TourPackageRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@Disabled
class TourPackageTest {

    @Autowired
    TourPackageRepository repository;
    @Autowired
    AccountsRepository accountsRepository;

    @Test
    @Transactional
    @Rollback(value = true)
    void tripPackSave() {
        var tp = TourPackage.builder()
                .title("신비한 제목")
                .description("떠나요 여행을")
                .account(accountsRepository.findAll().get(0))
                .price(BigDecimal.valueOf(1399.1563))
                .type("오사카")
                .build();

        repository.save(tp);
        repository.flush();

        System.out.println("----------->>>");
        repository.findAll().forEach(System.out::println);
    }

    @Test
    void findTripPack(){

    }

}