package com.healthtrip.travelcare.repository.location;

import com.healthtrip.travelcare.entity.account.AccountAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AccountAddress,Long> {

}
