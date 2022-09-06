package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.entity.account.AccountTimeToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountTimeTokenRepository extends JpaRepository<AccountTimeToken,Long> {

    void deleteByEmail(String email);
}
