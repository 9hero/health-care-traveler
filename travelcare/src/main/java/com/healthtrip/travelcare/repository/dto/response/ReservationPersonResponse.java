package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.ReservationPerson;
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
        private Long id;
        private String firstName;
        private String lastName;
        private ReservationPerson.Gender gender;
        private LocalDate birth;
        private String phone;
        private String emergencyContact;
        @Schema(description = "아직 구현 안함 필요시 구현")
        private AddressResponse addressResponse;
    }
}
