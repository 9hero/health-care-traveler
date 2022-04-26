package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.ReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationInfoRepository extends JpaRepository<ReservationInfo,Long> {
}
