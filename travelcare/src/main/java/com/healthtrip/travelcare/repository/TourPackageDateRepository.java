package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourPackageDateRepository extends JpaRepository<TourPackageDate,Long> {
}
