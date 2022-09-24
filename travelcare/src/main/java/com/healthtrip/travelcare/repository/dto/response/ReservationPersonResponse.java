package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

public class ReservationPersonResponse {

    @Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Schema(name = "예약자의 인적사항")
    public static class rpInfo implements Serializable {
        private Long reservedPersonId;
        private String firstName;
        private String lastName;
        private Booker.Gender gender;
        private LocalDate birth;
        private String phone;
        private String emergencyContact;
        private Long addressId;
        private String simpleAddress;
//        @Schema(description = "아직 구현 안함 필요시 구현")
//        private AddressResponse addressResponse;

        public static rpInfo toResponse(Booker booker) {
            return rpInfo.builder()
                    .reservedPersonId(booker.getId())
//                        .addressId(person.getBookerAddress().getId())
                    .simpleAddress(booker.getSimpleAddress())
                    .birth(booker.getBirth())
                    .firstName(booker.getFirstName())
                    .lastName(booker.getLastName())
                    .gender(booker.getGender())
                    .emergencyContact(booker.getEmergencyContact())
                    .phone(booker.getPhone())
                    .build();
        }
    }

}
