package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.ProgramCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramCheckupCategoriesRepo extends JpaRepository<ProgramCategory,Long> {

    @Modifying
    @Query("delete from ProgramCategory p where p.id in :programCategoryIds")
    void deleteAllByIds(@Param(value = "programCategoryIds") List<Long> programCategoryIds);
}
