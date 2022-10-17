package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {

    @Query("select h from Hospital h where h.id in :ids")
    List<Hospital> test(@Param("ids") List<Long> ids);
}
