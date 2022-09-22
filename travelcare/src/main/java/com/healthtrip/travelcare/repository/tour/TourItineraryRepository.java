package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourItineraryRepository extends JpaRepository<TourItinerary,Long> {
    @Query("select distinct ti from TourItinerary ti " +
            "join fetch ti.itineraryElements tie " +
            "where ti.tourPackage.id = :tourPackageId " +
            "order by tie.sequence")
    List<TourItinerary> findWithElements(@Param(value = "tourPackageId") Long tourPackageId);

    List<TourItinerary> findByTourPackageId(Long tourPackageId);
}
