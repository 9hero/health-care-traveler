package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.ReservationPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationPersonRepository extends JpaRepository<ReservationPerson,Long> {
}
