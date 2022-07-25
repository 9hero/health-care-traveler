package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomTravelRequest {

    private String reservationId;
    private String title;
    private String question;

    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "답변 작성 Request",description = "커스텀 여행 의뢰시 예약 상태가 B로 변경 그후 Y or N 결정해서 내려줘야함")
    public static class Answer {
        private Long customTravelId;
        @Schema(description = "답변")
        private String answer;
        @Schema(description = "예약 상태 업데이트= 예약 가능:Y, 답변전:B(before), 불가능함:N")
        private ReservationInfo.Status answerStatus;
        // Y,N, 답변완료:Y, 답변전:Before, 불가능함:N
    }

    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "고객이 커스텀 여행 수정 Request")
    public static class ClientModify {
        private Long customTravelId;
        private String title;
        private String question;
    }
}
