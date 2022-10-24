package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.entity.reservation.ReservationRejection;
import com.healthtrip.travelcare.repository.dto.request.ReservationRejectionReq;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationDtoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import com.healthtrip.travelcare.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "예약 API")
public class ReservationController {

    private final String domain = "/reservation";
    private final ReservationService reservationService;

    @Operation(summary = "통합 예약",description = "투어와 검진 동시에 예약합니다.")
    @PostMapping(domain)
    public void a(@RequestBody ReservationRequest.Integration reservationDTO) {
        reservationService.integrationReserve(reservationDTO);
    }
    @Operation(summary = "내 예약 보기",description = "전체 예약을 확인합니다.")
    @GetMapping(domain)
    public List<ReservationDtoResponse> myReservation(@Parameter(name = "페이지요청",description = "기본 설정: 0page 10size",example = "{\"page\":0,\"size\":10}") @PageableDefault Pageable pageable) {
        return reservationService.findMyReservation(pageable);
    }
    @Operation(summary = "예약 상세 보기",description = "예약 상세내용을 확인합니다.")
    @GetMapping(domain+"/{id}")
    public ReservationDtoResponse.RVDetails myReservationInfo(@PathVariable("id") String reservationId) {
        return reservationService.findMyReservationDetail(reservationId);
    }


}