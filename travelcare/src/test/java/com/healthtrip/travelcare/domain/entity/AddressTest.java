package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.AddressRepository;
import com.healthtrip.travelcare.repository.CountryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressTest {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CountryRepository countryRepository;

    @Test
    @Transactional
    @Rollback
    @Disabled("만들다만코드")
    void BaseEntityTest(){
        //given reposave

        Address address = Address.builder()
                .address("한강다리")
                .addressDetail("근처에있음")
                .city("서울")
                .district("마포구")
                .postalCode("12345")
                .country(countryRepository.findAll().get(0))
                .build();
        addressRepository.save(address);
        addressRepository.flush();
        //when  find
        List<Address> addresses = addressRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));

        //then  list.get sout assert
        addresses.forEach(getedAddress -> {
            System.out.println("테스트");
            System.out.println(getedAddress);
            assertEquals(getedAddress.getId(),address.getId());
        });

    }
}