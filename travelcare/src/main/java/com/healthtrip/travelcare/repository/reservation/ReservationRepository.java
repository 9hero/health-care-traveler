package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
}