package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.HospitalReservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalReservationDtoResponse {
    private String programName;
    private LocalDateTime reservedAt;
    private BigDecimal hospitalTotalAmount;
    private int hospitalBookerCount;
    public static HospitalReservationDtoResponse toResponse(HospitalReservation hospitalReservation) {
        return HospitalReservationDtoResponse.builder()
                .hospitalTotalAmount(hospitalReservation.getAmount())
                .hospitalBookerCount(hospitalReservation.getManCount())
                .programName(hospitalReservation.getMedicalCheckupProgram().getProgramName())
                .reservedAt(hospitalReservation.getReservedTime())
                .build();
    }
}
