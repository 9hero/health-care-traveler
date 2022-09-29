package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface MedicalCheckupProgramRepo extends JpaRepository<MedicalCheckupProgram,Long> {
    Page<MedicalCheckupProgram> findByHospitalId(Long hospitalId, Pageable pageable);

    @Query("select distinct mcp from MedicalCheckupProgram mcp " +
            "join mcp.programCategories pc " +
            "join pc.medicalCheckupCategory mcc " +
            "where mcc.id in :categoryIds")
    List<MedicalCheckupProgram> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    @Query("select distinct mcp from MedicalCheckupProgram mcp " +
            "join fetch mcp.hospital h " +
            "join fetch h.hospitalAddress " +
            "where mcp.id = :programId")
    MedicalCheckupProgram programDetailsById(@Param(value = "programId") Long programId);
}
