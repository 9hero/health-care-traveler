package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.TestEntityRepository;
import com.healthtrip.travelcare.repository.dto.HelloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    TestEntityRepository testEntityRepository;

    public HelloDTO helloService() {
        String name = testEntityRepository.findById(1L).get().getName();
        return HelloDTO.builder().name(name).build();
    }
}
