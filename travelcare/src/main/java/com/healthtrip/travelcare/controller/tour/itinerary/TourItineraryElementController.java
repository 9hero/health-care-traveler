package com.healthtrip.travelcare.controller.tour.itinerary;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.repository.dto.request.TourItineraryElementRequest;
import com.healthtrip.travelcare.service.TourItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "투어 요소 API")
public class TourItineraryElementController {

    private final String domain = "/tour/package/itinerary/element";
    private final String targetDomain = "/tour/package/itinerary/{id}/element";
    private final String admin = "/admin"+domain;
    private final String targetAdmin = "/admin"+targetDomain;

    /*
        관리자 API
     */
    private final TourItineraryService tourItineraryService;

    @Operation(summary = "일정 요소 등록")
    @PostMapping(targetAdmin)
    public void addElement(@PathVariable(name = "id") Long itineraryId,
                           @RequestBody List<TourItineraryElementRequest> request) {
        tourItineraryService.addTourItineraryElement(itineraryId,request);
    }

    @Operation(summary = "일정 요소 수정")
    @PutMapping(admin+"/{id}")
    public void modifyElement(@PathVariable(name = "id") Long elementId,
                           @RequestBody TourItineraryElementRequest request) {
        tourItineraryService.modifyTourItineraryElement(elementId,request);
    }
}
