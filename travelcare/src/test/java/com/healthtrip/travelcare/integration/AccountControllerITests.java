package com.healthtrip.travelcare.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.repository.AccountAgentRepository;
import com.healthtrip.travelcare.repository.AccountCommonRepository;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.request.AddressRequest;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerITests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    // account repositories
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private AccountCommonRepository accountCommonRepository;
    @Autowired
    private AccountAgentRepository accountAgentRepository;

    @BeforeEach
    void setup() {
        accountCommonRepository.deleteAllInBatch();
        accountAgentRepository.deleteAllInBatch();
        accountsRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("일반회원 가입 테스트-가입성공")
    void common_signup_ok() {
        //given
        String json = "{\n" +
                "  \"email\": \"gogmow@naver.com\",\n" +
                "  \"password\": \"ahah99\",\n" +
                "  \"addressData\": {\n" +
                "    \"address1\": \"주소1\",\n" +
                "    \"address2\": \"주소2\",\n" +
                "    \"district\": \"지역\",\n" +
                "    \"cityName\": \"도시명\",\n" +
                "    \"postalCode\": \"12345\",\n" +
                "    \"countryId\": 1\n" +
                "  },\n" +
                "  \"personDataRequest\": {\n" +
                "    \"firstName\": \"머머\",\n" +
                "    \"lastName\": \"구\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"birth\": \"2022-08-16\",\n" +
                "    \"phone\": \"string\",\n" +
                "    \"emergencyContact\": \"string\"\n" +
                "  }\n" +
                "}";

        //when
        try {
        ResultActions response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/account/common")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                );

        //then
        response.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(result -> result.getResponse().getContentAsString().equals("가입 완료"))
                .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("기관 유저 가입- 가입성공")
    void agentSignUp() {
        // given
        String json = "{\n" +
                "  \"email\": \"agent@email.com\",\n" +
                "  \"password\": \"string\",\n" +
                "  \"name\": \"77\",\n" +
                "  \"companyNumber\": \"12345\"\n" +
                "}";
        //when
        try {
            ResultActions response = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/api/account/agent")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
            );

            //then
            response.andExpect(MockMvcResultMatchers.status().is(200))
                    .andExpect(result -> result.getResponse().getContentAsString().equals("가입 완료"))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 로그인
    // jwt 토큰 재발급
    // 이메일 체크 & 이메일 인증코드 전송
    // 이메일 인증코드 확인
    // 비번 재설정 요청
    // 비번 재설정
    /*
        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
     */

}
