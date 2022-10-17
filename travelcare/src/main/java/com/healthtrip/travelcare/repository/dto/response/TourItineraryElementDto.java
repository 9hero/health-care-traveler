package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceList;
import com.healthtrip.travelcare.repository.dto.request.TourPlaceListRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourItineraryElementDto {

    private String title;

    private TourItineraryElement.ElementType elementType;

    @Schema(description = "일정 순서")
    private short sequence;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AddPlaceList {
        private TourItineraryElementDto tourItineraryElementDto;
        private List<TourPlaceListRequest> tourPlaceListRequestList;
    }

}
