package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import com.healthtrip.travelcare.repository.dto.response.AccountResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountsRepository accountsRepository;

    @Test
    @Disabled // 서비스 단위테스트 미작성
    public void create() {
        String email = "testEmail";
        //given
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .id(3L)
                .email(email)
                .password("testword")
                .status(Account.Status.N)
                .userRole(Account.UserRole.ROLE_COMMON)
                .build();
        Account account = accountRequestDto.toEntity();

        given(accountsRepository.save(any())).willReturn(account);
        given(accountsRepository.findByEmail(email)).willReturn(account); // identity라서 id 못알아냄

        //when
//        when(accountService.create(accountRequestDto)).thenReturn();

        Account foundAccount = accountsRepository.findByEmail(email);

        Assertions.assertEquals(foundAccount.getEmail(),account.getEmail());


    }

}