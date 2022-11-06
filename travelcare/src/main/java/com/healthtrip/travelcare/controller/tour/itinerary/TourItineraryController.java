package com.healthtrip.travelcare.controller.tour.itinerary;

import com.healthtrip.travelcare.repository.dto.request.TourItineraryRequest;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryResponse;
import com.healthtrip.travelcare.service.TourItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
//@Tag(name = "투어 일정 API")
@Tag(name = "투어 패키지 API")
public class TourItineraryController {

    private final String domain = "/tour/package/itinerary";
    private final String targetDomain = "/tour/package/{id}/itinerary";
    private final String admin = "/admin"+domain;
    private final String targetAdmin = "/admin"+targetDomain;

    @Autowired
    private final TourItineraryService tourItineraryService;

    @Tag(name = "투어 패키지 API")
    @Operation(summary = "패키지 id로 일정과 구성요소를 가져옵니다(구성요소의 장소제외")
    @GetMapping("/tour/package/{id}/itinerary")
    public List<TourItineraryResponse.WithElements> getTourItineraries(@PathVariable(name = "id") Long tourPackageId) {
        return tourItineraryService.getTourItineraries(tourPackageId);
    }

    /* 관리자 */
    @Tag(name = "투어 패키지 API")
    @Operation(summary = "패키지에 일정를 추가합니다")
    @PostMapping(targetAdmin)
    public void addTourItineraries(@PathVariable(name = "id") Long tourPackageId,
                                   @RequestBody List<TourItineraryRequest> tourItineraryRequests) {
        tourItineraryService.addTourItinerary(tourPackageId,tourItineraryRequests );
    }

    @Operation(summary = "해당 일정를 수정합니다", description = "덮어씌우기 방식, 주의 사항 <br/> 주의 put 방식이라 null 항목의 데이터는 삭제 됩니다.")
    @PutMapping(admin+"/{id}")
    public void updateItinerary(@PathVariable(name = "id") Long itineraryId,
                                   @RequestBody TourItineraryRequest updateRequest) {
        tourItineraryService.updateItinerary(itineraryId,updateRequest );
    }


    @Tag(name = "투어 패키지 API")
    @Operation(summary = "패키지에 일정과 요소를 추가합니다")
    @PostMapping("/admin/tour/package/{id}/itineraries")
    public void addTourItineraryAndElements(@PathVariable(name = "id") Long tourPackageId,
                                   @RequestBody List<TourItineraryRequest.WithElementAndPlaceList> request) {
        tourItineraryService.addTourItinerariesWithElements(tourPackageId, request);
    }
}
