package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourItineraryElementRequest {

    private String title;

    private TourItineraryElement.ElementType elementType;

    @Schema(description = "일정 순서")
    private short sequence;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AddPlaceList {
        private TourItineraryElementRequest elementRequest;
        private List<TourPlaceListRequest> tourPlaceListRequestList;
    }
}
