package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDateRepository extends JpaRepository<ReservationDate,Long> {
}
