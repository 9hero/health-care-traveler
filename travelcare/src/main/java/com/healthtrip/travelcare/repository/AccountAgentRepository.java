package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.AccountAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAgentRepository extends JpaRepository<AccountAgent,Long> {

}
