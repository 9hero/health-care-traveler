package com.healthtrip.travelcare.repository.dto.response;


import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackagePrices;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


public class TourPackageResponse {

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
    public static class InfoWithImage {

        private Long id;
        private String title;
        private TourPackagePrices packagePrices;
        private Long thumbnailID;
        private String thumbnailUrl;

        // 이미지 dto
        private List<TripPackageFileResponse.FileInfo> images;

        public static InfoWithImage toPackageInfoDTO(TourPackage tourPackage) {
            return InfoWithImage.builder()
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
    public static class TPImages {
        // 이미지 dto
        private List<TripPackageFileResponse.FileInfo> images;
    }
}
