package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.ReservationDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDateRepository extends JpaRepository<ReservationDate,Long> {
}
