package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.AddedCheckup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddedCheckupRepository extends JpaRepository<AddedCheckup, Long> {
}