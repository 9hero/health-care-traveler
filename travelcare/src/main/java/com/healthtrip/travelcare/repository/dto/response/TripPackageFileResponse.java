package com.healthtrip.travelcare.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class TripPackageFileResponse {

    @Data
    @Builder
    @Schema(name = "메인페이지에서 쓸 패키지 이미지 Response")
    public static class MainPagePackImage {
        private Long id;

        private String url;
    }

    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "파일정보(이미지) Response")
    public static class FileInfo {
        private Long id;

        private String url;

        private String name;
    }

}
