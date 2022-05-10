package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.config.DataSourceConfig;
import com.healthtrip.travelcare.repository.CountryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


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