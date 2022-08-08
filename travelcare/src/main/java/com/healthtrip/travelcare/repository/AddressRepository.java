package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.location.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
