package com.healthtrip.travelcare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.service.AccountService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("계정 생성 컨트롤러<->서비스 단위테스트")
    @Disabled("로컬호스트")
    void accounts() throws Exception {

        //given
        var accountRequestDto = AccountRequest.SignUpDto.builder()
                        .email("testEmail")
                                .password("testword")
//                                        .status(Account.Status.N)
                                                .userRole(Account.UserRole.ROLE_COMMON)
                                                        .build();

        given(accountService.create(accountRequestDto)).willReturn(ResponseEntity.created(URI.create("/")).build());
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
               // .andExpect(MockMvcResultMatchers.header().string("Location","/"))
                .andDo(MockMvcResultHandlers.print());
        verify(accountService,atLeast(1)).create(accountRequestDto);
    }

    @Test
    void login() {

    }
}