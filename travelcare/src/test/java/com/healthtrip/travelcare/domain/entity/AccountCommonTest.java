package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.AccountCommonRepository;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.AddressRepository;
import com.healthtrip.travelcare.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountCommonTest {

    @Autowired
    AccountCommonRepository accountCommonRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    EntityManager em;

    @Disabled
    @Test
    @Transactional
//    @Rollback(value = false)
    void saveAndFind() {
        //save
        var savedAccount = getCommonAccount();
        var savedAddress= getAddress();
        var common = AccountCommon.builder()
                .account(savedAccount)
                .address(savedAddress)
                .firstName("커몬계정")
                .lastName("일반계정")
                .gender('M')
                .birth(LocalDate.now())
                .phone("010-1234-1234")
                .emergencyContact("01031131515")
                .build();
        AccountCommon savedCommon = accountCommonRepository.save(common);
//        savedAccount.setAccountCommon(savedCommon); // 저장 후 바로 쓰려면 필요
        accountCommonRepository.flush();

        //find
        em.clear();
        System.out.println("------clear done ------");
//        var accountCommon = accountCommonRepository.findAll().get(0);
//        System.out.println("accountCommon's account: " + accountCommon.getAccount());
//        System.out.println("accountCommon : " + accountCommon);
        AccountCommon accountCommon = accountCommonRepository.findById(savedAccount.getId()).orElseThrow();
        System.out.println(accountCommon.getAccount());
        System.out.println(accountCommon.getFirstName());

//        var newAccount = accountsRepository.findByEmail(savedAccount.getEmail());
//        System.out.println("account : "+newAccount+" getEmail: "+newAccount.getEmail());


    }

    Account getCommonAccount() {
        var account= Account.builder()
                .email("test"+ Math.random())
                .password("pass1")
                .status(Account.Status.Y)
                .userRole(Account.UserRole.ROLE_COMMON)
                .build();
        return accountsRepository.save(account);
    }

    Address getAddress(){
        Address address = Address.builder()
                .address("accCommon")
                .addressDetail("123")
                .city("서울")
                .district("구로구")
                .postalCode("13545")
                .country(countryRepository.getById(9L))
                .build();
        return addressRepository.save(address);
    }
}