package com.healthtrip.travelcare.repository.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourItineraryDto {
    private Long itineraryId;
    // 일차
    private String day;
    // 지역
    private String location;
    // 지역상세
    private String specificLocations;

    // 숙소(테이블 분리 가능)
    private String accommodation;

    // 세부 일정
    private String details;

    // 일정 유의사항
    private String notice;

    // 일정 요소
    private List<TourItineraryElementDto> tourItineraryElements;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddItineraryRequest {

        // 일차
        private String day;
        // 지역
        private String location;
        // 지역상세
        private String specificLocations;
        // 숙소(테이블 분리 가능)
        private String accommodation;
        // 세부 일정
        private String details;
        // 일정 유의사항
        private String notice;
        // 일정 요소
        private List<TourItineraryElementDto.AddPlaceList> tourItineraryElements;
    }
}
