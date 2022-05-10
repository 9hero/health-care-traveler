package com.healthtrip.travelcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import com.healthtrip.travelcare.service.AccountService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class AccountControllerWholeTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Rollback
    void accounts() throws Exception {
        //given
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .email("testEmail")
                .password("testword")
                .status(Account.Status.N)
                .userRole(Account.UserRole.ROLE_COMMON)
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
                .andExpect(MockMvcResultMatchers.status().is(201))
                 .andExpect(MockMvcResultMatchers.header().string("Location","/"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("정확한 로그인,비밀번호 오류,이메일 오류")
    @Disabled(value = "db 데이터에 종속적")
    void login() {
        List<AccountRequestDto> requestDtoList = new ArrayList<>();
        requestDtoList.add(request("e","p"));
        requestDtoList.add(request("ea","p"));
        requestDtoList.add(request("e","ps"));
        try {
            int current = 0;
            for (AccountRequestDto dto : requestDtoList) {
                String requestJson = objectMapper.writeValueAsString(dto);
                switch (current) {
                    case 0:
                        mockMvc.perform(
                                        MockMvcRequestBuilders
                                                .post("http://localhost:8080/api/account/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(requestJson)
                                )
                                //then
                                .andExpect(MockMvcResultMatchers.status().is(200))
                                .andDo(MockMvcResultHandlers.print());
                        break;
                    case 1:
                        mockMvc.perform(
                                        MockMvcRequestBuilders
                                                .post("http://localhost:8080/api/account/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(requestJson)
                                )
                                //then
                                .andExpect(MockMvcResultMatchers.status().is(401))
                                .andExpect(MockMvcResultMatchers.content().string("아이디 오류"))
                                .andDo(MockMvcResultHandlers.print());
                        break;
                    case 2:
                        mockMvc.perform(
                                        MockMvcRequestBuilders
                                                .post("http://localhost:8080/api/account/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(requestJson)
                                )
                                //then
                                .andExpect(MockMvcResultMatchers.status().is(401))
                                .andExpect(MockMvcResultMatchers.content().string("비밀번호 오류"))
                                .andDo(MockMvcResultHandlers.print());
                        break;
                }
                current++;
            }



        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AccountRequestDto request(String email, String password) {
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .email(email)
                .password(password)
                .build();
        return accountRequestDto;
    }

}