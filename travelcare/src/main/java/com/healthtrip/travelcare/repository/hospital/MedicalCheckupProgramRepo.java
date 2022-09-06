package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalCheckupProgramRepo extends JpaRepository<MedicalCheckupProgram,Long> {
}
