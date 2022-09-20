package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourItineraryRepository extends JpaRepository<TourItinerary,Long> {
    List<TourItinerary> findByTourPackageId(Long tourPackageId);
}
