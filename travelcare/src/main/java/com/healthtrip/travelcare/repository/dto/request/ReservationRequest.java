package com.healthtrip.travelcare.repository.dto.request;


import com.healthtrip.travelcare.domain.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReservationRequest {


    @Getter
    @AllArgsConstructor
    @Schema(name = "예약 등록 정보 Request",
            description = "계정id, 역할:역할로 분기해서 예약할거라 필요함" +
                    "\n 패키지날짜 id, 예약인원수,주소정보(여기서 역할 분기한거사용: 주소 불러오기 향후 프론트에서 주소id 주길 바람)" +
            "\n 예약자 인적사항: 현재는 한명만 받음 가족단위 필요시 변경")
    public static class ReserveData{
        private Long userId; // 일반유저일시 주소는 db에서 찾음
        private Account.UserRole role;
        private Long dateId;
        private short personCount;
        private AddressRequest addressData;
        private ReservationPersonRequest reservationPersonData;
    }
}
