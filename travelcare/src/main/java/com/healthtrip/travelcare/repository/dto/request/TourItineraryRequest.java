package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.repository.dto.response.TourItineraryElementResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourItineraryRequest {
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WithElementAndPlaceList {
        private TourItineraryRequest tourItineraryRequest;
        // 일정 요소
        private List<TourItineraryElementRequest.AddPlaceList> tourItineraryElements;
    }
}
