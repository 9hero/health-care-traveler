package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.response.TourItineraryDto;
import com.healthtrip.travelcare.service.TourItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "투어 일정 API")
public class TourItineraryController {

    private final String domain = "/tour/package/itinerary";
    private final String admin = "/admin"+domain;

    @Autowired
    TourItineraryService tourItineraryService;

    @Tag(name = "투어 패키지 API")
    @Operation(summary = "패키지 id로 일정을 가져옵니다")
    @GetMapping("/tour/package/{id}/itinerary")
    public List<TourItineraryDto> getTourItineraries(@PathVariable(name = "id") Long tourPackageId) {
        return tourItineraryService.getTourItineraries(tourPackageId);
    }

}
