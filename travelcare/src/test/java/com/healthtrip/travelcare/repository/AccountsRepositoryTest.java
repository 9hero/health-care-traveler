package com.healthtrip.travelcare.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class AccountsRepositoryTest {

    @Autowired
    AccountsRepository accountsRepository;

    @Test
    @Transactional
    void existsByEmail() {
        System.out.println(accountsRepository.existsByEmail("e")); // True
        System.out.println(accountsRepository.existsByEmail("b")); // false
    }
}