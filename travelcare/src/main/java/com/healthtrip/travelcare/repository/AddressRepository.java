package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
