package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TourOptionRepository extends JpaRepository<TourOption, Long> {

    List<TourOption> findByTourPackageId(Long tourPackageId);
    List<TourOption> findByTourPackage(TourPackage tourPackageId);

}