package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.reservation.ReservationInquiryChat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationInquiryChatResponse {

    private String chat;
    private ReservationInquiryChat.Writer writer;
    private LocalDateTime writeAt;
    private LocalDateTime updateAt;
    public static ReservationInquiryChatResponse toResponse(ReservationInquiryChat reservationInquiryChat) {
           return ReservationInquiryChatResponse.builder()
                    .chat(reservationInquiryChat.getChat())
                    .writeAt(reservationInquiryChat.getCreatedAt())
                    .writer(reservationInquiryChat.getWriter())
                    .updateAt(reservationInquiryChat.getUpdatedAt())
                    .build();
    }
}
