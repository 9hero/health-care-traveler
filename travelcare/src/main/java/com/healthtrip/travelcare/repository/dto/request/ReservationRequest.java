package com.healthtrip.travelcare.repository.dto.request;


import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class ReservationRequest {


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "예약 등록 정보 Request",
            description = "계정id, 역할:역할로 분기해서 예약할거라 필요함" +
                    "<br/> 패키지날짜 id, 예약인원수,주소정보(여기서 역할 분기한거사용: 주소 불러오기 향후 프론트에서 주소id 주길 바람)")
    public static class ReserveData{
        private Account.UserRole role;
        private Long dateId;
        private AddressType addressType;
        private short personCount;
        private List<AddressRequest> addressData;
        private List<PersonData> reservationPersonData;
    }
    public static class TourR {
        private Long userId;
        private Long packageId;
        private short manCount;


    }
    public static class HospitalR {

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Integration {
        private List<PersonData> reservationPersonData;
        private TourR tour;
        private HospitalR hospital;
    }
    public enum AddressType{
        SINGLE,
        FOREACH
        ;
    }
}
