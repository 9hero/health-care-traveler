package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.HospitalReservation;

import com.healthtrip.travelcare.entity.reservation.AddedCheckup;
import com.healthtrip.travelcare.repository.dto.request.MedicalCheckupOptionalReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalReservationDtoResponse {
    private String programName;
    private LocalDateTime reservedAt;
    private BigDecimal hospitalTotalAmount;
    private int hospitalBookerCount;
    private List<AddedCheckupResponse> addedCheckupResponses;
    public static HospitalReservationDtoResponse toResponse(HospitalReservation hospitalReservation) {
        return HospitalReservationDtoResponse.builder()
                .hospitalTotalAmount(hospitalReservation.getAmount())
                .hospitalBookerCount(hospitalReservation.getPersonCount())
                .programName(hospitalReservation.getMedicalCheckupProgram().getProgramName())
//                .addedCheckupResponses(AddedCheckupResponse)
                .reservedAt(hospitalReservation.getReservedTime())
                .build();
    }
    public static HospitalReservationDtoResponse toResponse(HospitalReservation hospitalReservation,
                                                            List<AddedCheckupResponse> addedCheckupResponses) {
        return HospitalReservationDtoResponse.builder()
                .hospitalTotalAmount(hospitalReservation.getAmount())
                .hospitalBookerCount(hospitalReservation.getPersonCount())
                .programName(hospitalReservation.getMedicalCheckupProgram().getProgramName())
                .addedCheckupResponses(addedCheckupResponses)
                .reservedAt(hospitalReservation.getReservedTime())
                .build();
    }
}
