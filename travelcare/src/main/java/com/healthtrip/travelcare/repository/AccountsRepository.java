package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account,Long> {
}
