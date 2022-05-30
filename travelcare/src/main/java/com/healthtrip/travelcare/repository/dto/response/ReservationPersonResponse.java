package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.ReservationPerson;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class ReservationPersonResponse {

    @Data
    @AllArgsConstructor
    @Builder
    @Schema(name = "예약자의 인적사항")
    public static class rpInfo {
        private Long reservedPersonId;
        private String firstName;
        private String lastName;
        private PersonData.Gender gender;
        private LocalDate birth;
        private String phone;
        private String emergencyContact;
        private Long addressId;
//        @Schema(description = "아직 구현 안함 필요시 구현")
//        private AddressResponse addressResponse;
    }
}
