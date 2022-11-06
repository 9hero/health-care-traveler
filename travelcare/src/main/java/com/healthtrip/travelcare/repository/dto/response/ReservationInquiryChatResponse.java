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
    private Long chatId;
    private String chat;
    private String writer;
    private LocalDateTime writeAt;
    private LocalDateTime updateAt;
    public static ReservationInquiryChatResponse toResponse(ReservationInquiryChat reservationInquiryChat) {
           return ReservationInquiryChatResponse.builder()
                   .chatId(reservationInquiryChat.getId())
                    .chat(reservationInquiryChat.getChat())
                    .writeAt(reservationInquiryChat.getCreatedAt())
                    .writer(reservationInquiryChat.getWriter().getWriterName())
                    .updateAt(reservationInquiryChat.getUpdatedAt())
                    .build();
    }
}
