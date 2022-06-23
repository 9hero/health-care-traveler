package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@NoArgsConstructor
@Schema(name = "로그인 Response")
public class AccountResponse {
    @Builder
    public AccountResponse(Long id, String email, Account.Status status, Account.UserRole userRole, String jwt, Long refreshTokenId, String refreshToken) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.userRole = userRole;
        this.jwt = jwt;
        this.refreshTokenId = refreshTokenId;
        this.refreshToken = refreshToken;
    }



    private Long id;

    private String email;

    private Account.Status status;

    private Account.UserRole userRole;

    private String jwt;

    @Schema(name = "재생성 토큰 index id",description = "refresh token 식별번호 중요: use for validation")
    private Long refreshTokenId;

    private String refreshToken;
}
