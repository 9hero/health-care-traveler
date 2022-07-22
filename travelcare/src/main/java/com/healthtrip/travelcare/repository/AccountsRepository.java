package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account,Long> {

    boolean existsByEmail(String email);

    Account findByEmail(String email);
}
