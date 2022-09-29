package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupOptional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalCheckupOptionalRepo extends JpaRepository<MedicalCheckupOptional,Long> {
    @Query("select mco from MedicalCheckupOptional mco " +
            "join fetch mco.medicalCheckupItem " +
            "join mco.hospital h " +
            "where h.id = :hospitalId")
    List<MedicalCheckupOptional> findOptions(@Param(value = "hospitalId") Long hospitalId);
}
