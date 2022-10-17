package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourPackageFileResponse {
    private Long fileId;
    private String name;
    private String url;

    public static TourPackageFileResponse toResponse(TourPackageFile tourPackageFile) {
        return TourPackageFileResponse.builder()
                .fileId(tourPackageFile.getId())
                .name(tourPackageFile.getUrl())
                .url(tourPackageFile.getUrl())
                .build();
    }
}
