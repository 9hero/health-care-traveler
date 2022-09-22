package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.response.TourItineraryDto;
import com.healthtrip.travelcare.service.TourItineraryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "여행 일정 API")
public class TourItineraryController {

    private final String domain = "/tour/package/itinerary";
    private final String admin = "/admin"+domain;

    @Autowired
    TourItineraryService tourItineraryService;

    @GetMapping("/tour/package/{id}/itinerary")
    public List<TourItineraryDto> getTourItineraries(@RequestParam Long tourPackageId) {
        return tourItineraryService.getTourItineraries(tourPackageId);
    }

}
