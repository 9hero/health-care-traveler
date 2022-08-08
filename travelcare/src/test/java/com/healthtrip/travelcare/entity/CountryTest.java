package com.healthtrip.travelcare.entity;


import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.repository.CountryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class CountryTest {

    @Autowired
    CountryRepository countryRepository;

    @Test
    @Transactional
    @Rollback
    @Disabled(value = "중복 데이터")
    void countrySave(){

        Country countrySaved = countryRepository.save(Country.builder().id(1L).name("korea").build());
        System.out.println("------------>>>----------"+countrySaved.getCreatedAt());
    }
}