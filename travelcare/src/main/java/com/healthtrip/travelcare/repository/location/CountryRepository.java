package com.healthtrip.travelcare.repository.location;


import com.healthtrip.travelcare.entity.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends JpaRepository<Country,Long> {
    @Query(value = "insert into country values (:id,:name)",nativeQuery = true)
    void uniqueConflictTest(Long id , String name);
}
