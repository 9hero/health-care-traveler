package com.healthtrip.travelcare.repository.dto.request;


import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupOptional;
import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TourR {

        private Long packageId;
        private Short adultCount;
        private Short childCount;
        private Short infantCount;
        private LocalDateTime reservedAt;
        private BigDecimal tourTotalAmount;

    }
    @NoArgsConstructor
    @Getter
    @Setter
    public static class HospitalR {
        private Long medicalProgramId;
        private Short manCount;
        private LocalDateTime reservedAt;
        private BigDecimal hospitalTotalAmount;
        private List<Long> medicalCheckUpOptionalIds;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Integration {
        private String reservationName;
        private TourR tour;
        private List<TourOptionsRequest> tourOptions;
        private HospitalR hospital;
        private List<BookerRequest> bookerData;
        private BigDecimal totalAmount;
    }
    public enum AddressType{
        SINGLE,
        FOREACH
        ;
    }
}
