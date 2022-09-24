package com.healthtrip.travelcare.repository.dto.request;

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
    @NoArgsConstructor
    @Builder
    @Schema(name = "일반유저 회원가입 Request")
    public static class CommonSignUp {
        private String email;
        private String password;
        private PersonData personDataRequest;
    }
    @Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Schema(name = "기관유저 회원가입 Request")
    public static class AgentSignUp {
        private String email;
        private String password;
        private String name;
        private String companyNumber;
        private String companyContact;
        private AddressRequest companyAddress;
    }
    @Data
    @NoArgsConstructor
    @Schema(name = "로그인 Request")
    public static class SignInDto {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "잃어버린 패스워드 재설정")
    public static class PasswordReset {
        private Long tokenId;
        private String email;
        private String authToken;
        private String password;
    }
}
