package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourItineraryElementDto {

    private String title;

    private TourItineraryElement.ShowType showType;

    @Schema(description = "일정 순서")
    private short sequence;
}
