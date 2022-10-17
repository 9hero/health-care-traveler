package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourItineraryElementRepository extends JpaRepository<TourItineraryElement,Long> {

//    @EntityGraph(attributePaths = {})
    @Query("select tie from TourItineraryElement tie " +
            "join fetch tie.tourItinerary ti " +
//            "join fetch tourPlaceLists tpl " +
//            "join fetch tpl.tourPlace " +
            "where ti.id in :tourItineraries")
    List<TourItineraryElement> findByTourItinerary(@Param("tourItineraries") List<Long> tourItineraries);
}
