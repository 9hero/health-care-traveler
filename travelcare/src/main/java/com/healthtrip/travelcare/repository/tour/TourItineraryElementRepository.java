package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourItineraryElementRepository extends JpaRepository<TourItineraryElement,Long> {
}
