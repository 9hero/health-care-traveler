package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.domain.entity.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
@EqualsAndHashCode
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
