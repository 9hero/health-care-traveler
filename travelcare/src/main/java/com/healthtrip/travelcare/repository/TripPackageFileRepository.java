package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.TripPackageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripPackageFileRepository extends JpaRepository<TripPackageFile,Long> {

    List<TripPackageFile> findByTripPackageId(Long tripPackageId);
}
