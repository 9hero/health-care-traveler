package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalCheckupCategoryRepo extends JpaRepository<MedicalCheckupCategory,Long> {
}
