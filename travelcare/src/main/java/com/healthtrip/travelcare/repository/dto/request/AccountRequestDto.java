package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.domain.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
@EqualsAndHashCode
@Schema(name = "(REQUEST) 계정 API",description = "회원 가입 시 email & password만 작성해서 보내주세요")
public class AccountRequestDto {

    @Builder
    public AccountRequestDto(Long id, String email, String password, Account.Status status, Account.UserRole userRole) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.userRole = userRole;
    }
    private Long id;

    @NotBlank(message = "이메일은 비울 수 없습니다.")
    private String email;

    private String password;

    @Schema(description = "Y: 인증 완료 N: 인증전 B: 차단")
    private Account.Status status;

    private Account.UserRole userRole;

    public Account toEntity(){
        return Account.builder()
                .email(email)
                .password(password)
                .userRole(Account.UserRole.ROLE_COMMON)
                .status(Account.Status.N)
                .build();
    }

}
