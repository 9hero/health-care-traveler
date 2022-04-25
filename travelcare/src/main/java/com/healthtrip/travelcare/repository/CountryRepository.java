package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Long> {
}
