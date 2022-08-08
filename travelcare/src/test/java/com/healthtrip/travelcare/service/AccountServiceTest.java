package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
//    @InjectMocks
//    private AccountService accountService;

    @Mock
    private AccountsRepository accountsRepository;

    @Test
    @Disabled // 서비스 단위테스트 미작성
    public void create() {
        String email = "testEmails";
        //given
        var accountRequestDto = AccountRequest.CommonSignUp.builder()
//                .id(3L)
                .email(email)
                .password("testWords")
//                .status(Account.Status.N)
//                .userRole(Account.UserRole.ROLE_COMMON)
                .build();

        Account account = Account.builder()
//                .email(accountRequestDto.getEmail())
//                .password(accountRequestDto.getPassword())
                .status(Account.Status.N)
                .userRole(Account.UserRole.ROLE_COMMON)
                .build();

        given(accountsRepository.save(any())).willReturn(account);
//        given(accountsRepository.findByEmail(email)).willReturn(account); // identity라서 id 못알아냄

        //when
//        var result = accountService.create(accountRequestDto);

        Account foundAccount = accountsRepository.findByEmail(email);

//        Assertions.assertEquals();
        Assertions.assertEquals(foundAccount.getEmail(),account.getEmail());


    }

}