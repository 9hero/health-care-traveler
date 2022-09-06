package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalCheckupItemRepo extends JpaRepository<MedicalCheckupItem,Long> {
}
