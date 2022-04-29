package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {

    private String email;

    private String password;

    private Account.Status status;

    private Account.UserRole userRole;

}
