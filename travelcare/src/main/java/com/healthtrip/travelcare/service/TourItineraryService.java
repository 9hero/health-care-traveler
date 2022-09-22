package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryDto;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryElementDto;
import com.healthtrip.travelcare.repository.tour.TourItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourItineraryService {

    @Autowired
    TourItineraryRepository tourItineraryRepository;

    @Transactional(readOnly = true)
    public List<TourItineraryDto> getTourItineraries(Long tourPackageId) {
        List<TourItineraryDto> itineraryList = new ArrayList<>();

        List<TourItinerary> tourItineraryList = tourItineraryRepository.findWithElements(tourPackageId);
        tourItineraryList.forEach(tourItinerary -> {
            var tourItineraryElementDtoList= tourItinerary.getItineraryElements().stream().map(
                    tourItineraryElement -> TourItineraryElementDto.builder()
                            .sequence(tourItineraryElement.getSequence())
                            .title(tourItineraryElement.getTitle())
                            .showType(tourItineraryElement.getShowType())
                            .build()
            ).collect(Collectors.toList());
            itineraryList.add(TourItineraryDto.builder()
                            .itineraryId(tourItinerary.getId())
                            .day(tourItinerary.getDay())
                            .accommodation(tourItinerary.getAccommodation())
                            .details(tourItinerary.getDetails())
                            .notice(tourItinerary.getNotice())
                            .location(tourItinerary.getLocation())
                            .specificLocations(tourItinerary.getSpecificLocations())
                            .tourItineraryElements(tourItineraryElementDtoList)
                    .build());
        });

        return itineraryList;
    }
}
