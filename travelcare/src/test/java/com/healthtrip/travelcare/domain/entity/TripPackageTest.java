package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class TripPackageTest {

    @Autowired
    TripPackageRepository repository;
    @Autowired
    AccountsRepository accountsRepository;

    @Test
    void tripPackSave() {
        var tp = TripPackage.builder()
                .description("떠나요 여행을")
                .account(accountsRepository.findById(4L).get())
                .peopleLimit((short) 5)
                .price(BigDecimal.valueOf(1399.1563))
                .type("오사카")
                .build();

        repository.save(tp);
        repository.flush();

        System.out.println("----------->>>");
        repository.findAll().forEach(System.out::println);
    }

}