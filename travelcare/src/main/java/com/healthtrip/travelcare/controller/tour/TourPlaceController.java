package com.healthtrip.travelcare.controller.tour;

import com.healthtrip.travelcare.repository.dto.request.TourPlaceRequest;
import com.healthtrip.travelcare.service.TourPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "투어 장소 API")
public class TourPlaceController {

    private final String domain = "/tour/place";
    private final String admin = "/admin"+domain;

    private final TourPlaceService tourPlaceService;

    @Operation(summary = "투어 장소를 추가합니다.")
    @PostMapping(admin)
    public void addTourPlace(TourPlaceRequest.WithPlaceImage tourPlaceRequest) {
        tourPlaceService.addPlace(tourPlaceRequest);
    }

    @Operation(summary = "투어 장소를 수정합니다.")
    @PutMapping(admin+"/{id}")
    public void modifyTourPlace(@PathVariable(name = "id") Long placeId
            ,@RequestBody TourPlaceRequest tourPlaceRequest) {
        tourPlaceService.modifyPlace(placeId,tourPlaceRequest);
    }
}
