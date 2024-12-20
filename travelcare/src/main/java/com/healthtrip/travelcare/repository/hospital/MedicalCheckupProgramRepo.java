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

    @Query(" select distinct mcp " +
            " from MedicalCheckupProgram mcp " +
            " join fetch mcp.hospital h " +
            " where mcp.id in  " +
            " (select mcp.id " +
            " from MedicalCheckupProgram mcp " +
            " inner join mcp.programCategories pc " +
            " inner join pc.medicalCheckupCategory mcc " +
            " where mcc.id in :categoryIds " +
            " group by mcp.id " +
            " having count(mcp.id) >=:size )")
    List<MedicalCheckupProgram> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds,@Param("size") long size);


    @Query("select distinct mcp from MedicalCheckupProgram mcp " +
            "join fetch mcp.hospital h " +
            "join fetch h.hospitalAddress " +
            "where mcp.id = :programId")
    MedicalCheckupProgram programDetailsById(@Param(value = "programId") Long programId);

    @Query("select distinct mcp from MedicalCheckupProgram mcp " +
//            "join fetch mcp.hospital h " +
//            "join fetch h.hospitalAddress " +
            "left join fetch mcp.programCategories pc " +
            "left join fetch pc.medicalCheckupCategory " +
            "where mcp.id = :programId")
    MedicalCheckupProgram programDetailsByIdForAdmin(@Param(value = "programId") Long programId);

    @Query("select distinct mcp from MedicalCheckupProgram mcp " +
            "join fetch mcp.programCategories pc " +
            "join fetch pc.medicalCheckupCategory mcc " +
            "where mcp.id =:programId")
    MedicalCheckupProgram findByIdAndCategoryIdsFetch(@Param(value = "programId") Long programId);

}
