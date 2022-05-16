package com.healthtrip.travelcare.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class NoticeBoardResponse {


    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "메인페이지 공지사항 Response")
    public static class mainPageNoticeBoard{

        private Long id;

        private String title;

        private LocalDate createdAt;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "공지 세부사항 Response")
    public static class NoticeBoardDetails{

        private Long id;

        private String title;

        @Schema(description = "공지사항 내용")
        private String announcement;

        private LocalDate createdAt;
    }
}
