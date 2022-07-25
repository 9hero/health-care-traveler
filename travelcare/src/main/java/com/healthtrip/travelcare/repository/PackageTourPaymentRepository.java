package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.travel.PackageTourPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageTourPaymentRepository extends JpaRepository<PackageTourPayment,String> {

    boolean existsById(String id);
}
