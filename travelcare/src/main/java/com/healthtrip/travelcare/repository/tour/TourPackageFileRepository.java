package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourPackageFileRepository extends JpaRepository<TourPackageFile,Long> {

    List<TourPackageFile> findByTourPackageId(Long tripPackageId);
}
