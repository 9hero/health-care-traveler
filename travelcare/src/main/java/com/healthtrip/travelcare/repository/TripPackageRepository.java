package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.travel.trip_package.TripPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPackageRepository extends JpaRepository<TripPackage,Long> {
}
