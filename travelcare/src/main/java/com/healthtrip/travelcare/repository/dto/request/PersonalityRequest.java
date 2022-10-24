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

    private Short friendlinessScore;

    private Short extroversionScore;

    private Short opennessScore;

}
