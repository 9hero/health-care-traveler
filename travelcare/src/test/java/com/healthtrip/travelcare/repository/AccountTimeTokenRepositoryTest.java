package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.AccountTimeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@SpringBootTest
class AccountTimeTokenRepositoryTest {

    @Autowired
    private AccountTimeTokenRepository accountTimeTokenRepository;

    @Test
    @Transactional
    void findByEmailAndAuthTokenExpireDateAfter(){
        String email = "rngnlah@naver.com"+ Math.random();
        String uuid = UUID.randomUUID().toString();

        var token = AccountTimeToken.builder()
                .email(email)
                .authToken(uuid)
                .expired(false)
                .build();

        accountTimeTokenRepository.save(token);
        try {
            var act =accountTimeTokenRepository.findByEmailAndAuthTokenAndExpirationDateAfter(email, uuid, LocalDateTime.now().plusDays(1L))
                    .orElseThrow(()->new Exception("토큰 못찾음"));
            Assertions.assertEquals(email,act.getEmail());
            Assertions.assertEquals(uuid,act.getAuthToken());
        } catch (Exception e) {
            System.out.println("account token failed");
            e.printStackTrace();
        }

    }
}