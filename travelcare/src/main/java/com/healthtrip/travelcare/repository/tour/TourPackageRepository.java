package com.healthtrip.travelcare.repository.tour;


import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourPackageRepository extends JpaRepository<TourPackage,Long> {

    @Query("select tp from TourPackage tp")
    Page<TourPackage> pagedTourPackage(Pageable pageable);

    @Query("select tp from TourPackage tp join fetch tp.mainImage")
    List<TourPackage> mainPageTourPackage();
}
