package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.ProgramCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramCheckupCategoriesRepo extends JpaRepository<ProgramCategory,Long> {
}
