package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.ReservationPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationPaymentRepository extends JpaRepository<ReservationPayment,String> {

    boolean existsById(String id);
}
