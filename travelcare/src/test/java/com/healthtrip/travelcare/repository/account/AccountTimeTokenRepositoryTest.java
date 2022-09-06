package com.healthtrip.travelcare.repository.account;


import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.AccountTimeToken;
import com.healthtrip.travelcare.repository.account.AccountTimeTokenRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaUnitTest
class AccountTimeTokenRepositoryTest{

    @Autowired
    private AccountTimeTokenRepository accountTimeTokenRepository;

    private AccountTimeToken timeToken;

    @BeforeEach
    void setup(){

        String email = "rngnlah@naver.com"+ Math.random();
        String uuid = UUID.randomUUID().toString();

        timeToken = AccountTimeToken.builder()
                .email(email)
                .authToken(uuid)
                .expired(false)
                .build();
    }

    @Test
    @DisplayName("단순 저장 테스트")
    void save(){
        //given
        AccountTimeToken token =

        // when
        accountTimeTokenRepository.save(timeToken);
        
        // then
        assertThat(token).isNotNull();
        assertThat(token.getId()).isGreaterThan(0);
        assertThat(token.getEmail()).isEqualTo(timeToken.getEmail());
        assertThat(token.getAuthToken()).isEqualTo(token.getAuthToken());

    }
    @Test
    @DisplayName("조회")
    void timeToken_findById_ReturnTimeToken(){
        // given
        accountTimeTokenRepository.save(timeToken);

        // when
        var savedAccountTimeToken = accountTimeTokenRepository.findById(timeToken.getId()).get();

        // then
        assertThat(savedAccountTimeToken).isNotNull();
    }
}