package com.healthtrip.travelcare.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalityRequest {

    private Float friendlinessScore;

    private Float extroversionScore;

    private Float opennessScore;

}
