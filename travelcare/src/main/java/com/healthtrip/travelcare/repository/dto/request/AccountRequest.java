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
    @Schema(name = "회원가입 Request")
    public static class SignUpDto {
        private String email;
        private String password;
        @Schema(description = "유저 권한",defaultValue = "ROLE_COMMON")
        private Account.UserRole userRole;
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
