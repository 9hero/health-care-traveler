package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourItineraryResponse {
    private Long id;

    // 일차
    @Schema(description = "일차")
    private String day;
    // 지역
    @Schema(description = "지역")
    private String location;
    // 지역상세
    @Schema(description = "지역상세")
    private String specificLocations;

    // 숙소(테이블 분리 가능)
    @Schema(name = "숙소")
    private String accommodation;

    // 세부 일정
    @Schema(name = "세부일정")
    private String details;

    // 일정 유의사항
    @Schema(name = "유의사항")
    private String notice;


    public static TourItineraryResponse toResponse(TourItinerary tourItinerary) {
        return TourItineraryResponse.builder()
                .id(tourItinerary.getId())
                .day(tourItinerary.getDay())
                .location(tourItinerary.getLocation())
                .specificLocations(tourItinerary.getSpecificLocations())
                .accommodation(tourItinerary.getAccommodation())
                .details(tourItinerary.getDetails())
                .notice(tourItinerary.getNotice())
                .build();
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WithElements{
        TourItineraryResponse tourItineraryResponse;
        List<TourItineraryElementResponse> tourItineraryElementResponses;
    }
}
