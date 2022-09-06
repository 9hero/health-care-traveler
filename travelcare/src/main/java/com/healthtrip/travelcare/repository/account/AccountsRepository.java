package com.healthtrip.travelcare.repository.account;


import com.healthtrip.travelcare.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account,Long> {

    boolean existsByEmail(String email);

    Account findByEmail(String email);
}
