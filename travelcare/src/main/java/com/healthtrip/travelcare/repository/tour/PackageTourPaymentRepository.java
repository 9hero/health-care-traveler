package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.PackageTourPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageTourPaymentRepository extends JpaRepository<PackageTourPayment,String> {

    boolean existsById(String id);
}
