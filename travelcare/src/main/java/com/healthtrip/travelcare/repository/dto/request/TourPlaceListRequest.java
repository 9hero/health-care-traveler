package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourPlaceListRequest {
    private Long placeId;
    private TourPlaceList.PlaceShowType placeShowType;
}
