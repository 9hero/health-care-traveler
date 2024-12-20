package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.reservation.Booker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Schema(name = "인적 사항 Request ")
public class PersonData {
    private String firstName;
    private String lastName;
    private Booker.Gender gender;
    private LocalDate birth;
    private String phone;
    private String emergencyContact;
    private AddressRequest addressRequest;


}
