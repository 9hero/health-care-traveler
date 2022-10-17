package com.healthtrip.travelcare.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


public class NoticeBoardRequest {

    @Data
    @NoArgsConstructor
    @Schema(name = "공지사항 작성 Request")
    public static class AddPost {
        private String title;
        private String announcement;

    }
    @Data
    @NoArgsConstructor
    @Schema(name = "공지사항 수정 Request")
    public static class Update {
        private Long noticePostId;
        private String title;
        private String announcement;

    }


}
