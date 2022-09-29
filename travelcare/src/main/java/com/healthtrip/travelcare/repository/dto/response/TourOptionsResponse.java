package com.healthtrip.travelcare.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourOptionsResponse {
    private Long optionId;
    private String optionName;
}
