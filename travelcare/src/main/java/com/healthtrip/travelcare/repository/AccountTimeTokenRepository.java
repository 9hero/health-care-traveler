package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.AccountTimeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountTimeTokenRepository extends JpaRepository<AccountTimeToken,Long> {

    Optional<AccountTimeToken> findByEmailAndAuthTokenAndExpirationDateAfter(String email, String authToken, LocalDateTime currentTime);
    Optional<AccountTimeToken> findByEmailAndAuthToken(String email, String authToken);

    void deleteByEmail(String email);
}