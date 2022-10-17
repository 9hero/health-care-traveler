package com.healthtrip.travelcare.repository.dto.response;


import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackagePrices;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourPackageResponse {

    private Long id;
    private String title;
    private String description;
    private TourPackagePrices packagePrices;
    private String standardOffer;
    private String nonOffer;
    private String notice;

    public static TourPackageResponse toResponse(TourPackage tourPackage) {
        return TourPackageResponse.builder()
                .id(tourPackage.getId())
                .title(tourPackage.getTitle())
                .description(tourPackage.getDescription())
                .packagePrices(tourPackage.getPrices())
                .standardOffer(tourPackage.getStandardOffer())
                .nonOffer(tourPackage.getNonOffer())
                .notice(tourPackage.getNotice())
                .build();
    }

    @Data
    @Builder
    @Schema(name = "메인 화면에 표시되는 여행패키지 정보 Response")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TPBasicInfo {
       private Long id;
       private String title;
       private TourPackagePrices packagePrices;
       private Long thumbnailID;
       private String thumbnailUrl;

       public static TourPackageResponse.TPBasicInfo toMainPageDTO(TourPackage tourPackage) {
            return TourPackageResponse.TPBasicInfo.builder()
                    .id(tourPackage.getId())
                    .title(tourPackage.getTitle())
                    .packagePrices(tourPackage.getPrices())
                    .thumbnailID(tourPackage.getMainImage().getId())
                    .thumbnailUrl(tourPackage.getMainImage().getUrl())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "여행 패키지 세부사항 Response (패키지,이미지,예약가능날짜)")
    public static class tourWithImages {
        private TourPackageResponse tourPackageResponse;
        // 이미지 dto
        private TourPackageFileResponse mainImage;
        private List<TourPackageFileResponse> tourImages;
    }
}
