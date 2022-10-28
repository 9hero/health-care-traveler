package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.account.Personality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalityResponse {

    private Long personalityId;
    private TendencyResponse tendencyResponse;

    public static PersonalityResponse toEntity(Personality personality) {
        return PersonalityResponse.builder()
                .personalityId(personality.getId())
                .tendencyResponse(TendencyResponse.toResponse(personality.getTendency()))
                .build();


    }
}
