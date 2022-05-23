package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.domain.entity.ReservationPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(name = "예약자 등록 Request ")
public class ReservationPersonRequest {
    private String firstName;
    private String lastName;
    private ReservationPerson.Gender gender;
    private LocalDate birth;
    private String phone;
    private String emergencyContact;
}
