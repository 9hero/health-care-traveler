package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.Booker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookerRepository extends JpaRepository<Booker, Long> {
}