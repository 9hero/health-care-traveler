package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@NoArgsConstructor
@Schema(name = "계정 응답 객체")
public class AccountResponseDto {
    @Builder
    public AccountResponseDto(Long id, String email, String password, Account.Status status, Account.UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.userRole = userRole;
    }

    private Long id;

    private String email;

    private String password;

    private Account.Status status;

    private Account.UserRole userRole;

}
