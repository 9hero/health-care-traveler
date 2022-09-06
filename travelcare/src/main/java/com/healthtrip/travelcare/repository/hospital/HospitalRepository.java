package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
