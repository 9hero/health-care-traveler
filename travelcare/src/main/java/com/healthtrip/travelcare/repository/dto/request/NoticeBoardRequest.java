package com.healthtrip.travelcare.repository.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;


public class NoticeBoardRequest {

    @Data
    @NoArgsConstructor
    public static class addNoticeBoard {
        private Long userId;
        private String title;
        private String announcement;

    }
    @Data
    @NoArgsConstructor
    public static class updateNoticeBoard{
        private Long noticePostId;
        private String title;
        private String announcement;

    }


}
