package com.healthtrip.travelcare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.service.AccountService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Disabled
class AccountControllerWholeTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Rollback
    @Disabled
    void accounts() throws Exception {
        //given
        AccountRequest.commonSignUp accountRequestDto = AccountRequest.commonSignUp.builder()
                .email("testEmail") // 사용가능한 이메일
//                .email("e") // 사용 불가능한 이메일
                .password("testword")
//                .status(Account.Status.N)
//                .userRole(Account.UserRole.ROLE_COMMON)
                .build();

        String requestJson = objectMapper.writeValueAsString(accountRequestDto);

        //when
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("http://localhost:8080/api/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());

    }
}