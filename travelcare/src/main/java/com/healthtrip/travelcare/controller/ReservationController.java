package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "예약 API")
public class ReservationController {
    private final String domain = "/reservation";
    private String admin = "/admin"+domain;
    private final ReservationService reservationService;

    @Operation(summary = "통합 예약",description = "투어와 검진 동시에 예약합니다.")
    @PostMapping("")
    public void a(@RequestBody ReservationRequest.Integration reservationDTO) {
        // 투어
        reservationService.reserveTour(reservationDTO.getTour());
        // 병원
        reservationService.reserveHospital(reservationDTO.getHospital());
    }
    @Operation(summary = "내 예약 보기",description = "전체 예약을 확인합니다.")
    @GetMapping("")
    public void myReservation() {

    }
}
