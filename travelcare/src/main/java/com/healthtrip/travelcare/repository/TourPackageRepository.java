package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourPackageRepository extends JpaRepository<TourPackage,Long> {
}
