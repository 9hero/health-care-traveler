package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalReservationRepository extends JpaRepository<HospitalReservation,Long> {
}
