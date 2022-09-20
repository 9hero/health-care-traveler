package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourItineraryRepository extends JpaRepository<TourItinerary,Long> {
}
