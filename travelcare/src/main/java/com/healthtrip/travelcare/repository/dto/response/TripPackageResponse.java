package com.healthtrip.travelcare.repository.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


public class TripPackageResponse {

    @Data
    @Builder
    @Schema(name = "메인 화면에 표시되는 여행패키지 정보 Response")
    public static class MainPagePack {
       private Long id;

       private String title;

       private BigDecimal price;

       // API 분리 시 front 에서 로딩이미지 필요
       private TripPackageFileResponse.MainPagePackImage thumbnail;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "여행 패키지 세부사항 Response (패키지,이미지,예약가능날짜)")
    public static class TripPackInfo {

        private Long packageId;
        private String title;
        private String description;
        private BigDecimal price;
        private String type;

        // 이미지 dto
        private List<TripPackageFileResponse.FileInfo> images;
        // 날짜 dto
        private List<ReservationDateResponse.DateInfoAll> dates;
    }
}
