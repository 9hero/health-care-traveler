package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.account.Tendency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TendencyResponse {

    private Long tendencyId;

    private String name;

    private String description;

    private String imageUrl;

    private String extroversionLevel;

    private String opennessLevel;

    private String friendlinessLevel;



    public static TendencyResponse toResponse(Tendency tendency) {
        return TendencyResponse.builder()
                .tendencyId(tendency.getId())
                .name(tendency.getName())
                .description(tendency.getDescription())
                .imageUrl(tendency.getImageFile().getUrl())
                .extroversionLevel(tendency.getExtroversionLevel().getLevel())
                .friendlinessLevel(tendency.getFriendlinessLevel().getLevel())
                .opennessLevel(tendency.getOpennessLevel().getLevel())
                .build();
    }
}
