package com.healthtrip.travelcare.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class NoticeBoardResponseDto {


    @Data
    @Builder
    @AllArgsConstructor
    public static class mainPageNoticeBoard{

        private Long id;

        private String title;

        private LocalDate createdAt;
    }
}
