package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@NoArgsConstructor
@Schema(name = "로그인 Response")
public class AccountResponse {
    @Builder
    public AccountResponse(Long id, String email, Account.Status status, Account.UserRole userRole) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.userRole = userRole;
    }

    private Long id;

    private String email;

    private Account.Status status;

    private Account.UserRole userRole;

}
