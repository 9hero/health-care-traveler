package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AccountTest {

    @Autowired
    AccountsRepository accountsRepository;

    @Test
    void findUser(){
        accountsRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Rollback
    @Transactional
    void entityTest(){

        Account account = new Account();
        account.setEmail("zero@gmail.com");
        account.setPassword("1234");
        account.setStatus(Account.Status.Y);
        account.setUserRole(Account.UserRole.ROLE_COMMON);

        accountsRepository.save(account);
        accountsRepository.flush();
        System.out.println("---->>>");

        accountsRepository.findAll().forEach(findedAccount-> {
            System.out.println("가입일"+findedAccount.getCreatedAt());
        });
    }
}