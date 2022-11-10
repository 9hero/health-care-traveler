package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.tour.reservation.ReservationInquiry;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationInquiryResponse {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "문의 제목")
    public static class InquiryList {
        private Long inquiryId;
        private String reservationId;
        private String reservationName;
        private String title;

        public static InquiryList toResponse(ReservationInquiry reservationInquiry) {
            return
                    ReservationInquiryResponse.InquiryList.builder()
                            .inquiryId(reservationInquiry.getId())
                            .reservationId(reservationInquiry.getReservation().getId())
                            .reservationName(reservationInquiry.getReservation().getTitle())
                            .title(reservationInquiry.getTitle())
                            .build();
        }
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(nullable = true)
    public static class Info{
        private Long inquiryId;
        private String title;
        private String question;
        private LocalDateTime writeAt;
        private LocalDateTime updateAt;
        private List<ReservationInquiryChatResponse> chatList;
    }

}
