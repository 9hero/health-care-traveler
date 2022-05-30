package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.domain.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
////@ToString
//@EqualsAndHashCode
//@Schema(name = "(REQUEST) 계정 API")
public class AccountRequest {


    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "일반유저 회원가입 Request")
    public static class commonSignUp{
        private String email;
        private String password;
        private AddressRequest addressData;
        private PersonData personDataRequest;
    }
    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "기관유저 회원가입 Request")
    public static class agentSignUp{
        private String email;
        private String password;
        private String name;
        private String companyNumber;
    }
    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "로그인 Request")
    public static class SignInDto {
        private String email;
        private String password;
    }
}
