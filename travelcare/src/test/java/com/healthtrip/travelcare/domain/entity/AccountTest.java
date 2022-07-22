package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.domain.entity.account.Account;
import com.healthtrip.travelcare.repository.AccountsRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Disabled
class AccountTest {


    @Autowired
    AccountsRepository accountsRepository;

    @Test
    void findUser(){
        accountsRepository.findAll().forEach(System.out::println);
//        System.out.println(accountsRepository.existsByEmail("e"));
    }

    @Test
    @Rollback
    @Transactional
    void entityTest(){

        Account account = Account.builder()
                .email("zero@gmail.com")
                .password("1234")
                .build();
//        account.setStatus(Account.Status.Y);
//        account.setUserRole(Account.UserRole.ROLE_COMMON);

        accountsRepository.save(account);
        accountsRepository.flush();
        System.out.println("---->>>");

        accountsRepository.findAll().forEach(findedAccount-> {
            System.out.println("유저 ROLE default 적용여부 "+findedAccount.getUserRole());
            System.out.println("유저 상태, 역할"+findedAccount.getStatus()+"\n"+findedAccount.getUserRole());
            System.out.println("가입일"+findedAccount.getCreatedAt());
        });
    }
}