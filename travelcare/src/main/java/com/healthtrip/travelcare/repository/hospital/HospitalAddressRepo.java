package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.HospitalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalAddressRepo extends JpaRepository<HospitalAddress,Long> {
}
