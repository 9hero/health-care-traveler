package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryDto;
import com.healthtrip.travelcare.repository.tour.TourItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourItineraryService {

    @Autowired
    TourItineraryRepository tourItineraryRepository;

    public List<TourItineraryDto> getTourItineraries(Long tourPackageId) {
        List<TourItineraryDto> list = new ArrayList<>();

        List<TourItinerary> tourItineraryList = tourItineraryRepository.findByTourPackageId(tourPackageId);
        tourItineraryList.forEach(tourItinerary -> {
                    tourItinerary.getItineraryElements().stream().map(tourItineraryElement -> tourItineraryElement.getTourPlaceLists());
                    list.add(TourItineraryDto.builder().build());
        });

        return null;
    }
}
