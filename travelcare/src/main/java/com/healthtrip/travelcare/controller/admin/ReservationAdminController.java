package com.healthtrip.travelcare.controller.admin;

import com.healthtrip.travelcare.repository.dto.request.ReservationRejectionReq;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationDtoResponse;
import com.healthtrip.travelcare.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "예약 API")
public class ReservationAdminController {
    private final String domain = "/reservation";
    private final String admin = "/admin"+domain;
    private final ReservationService reservationService;

    /* 어드민 API */
    @Operation(summary = "예약 모두 조회",description = "예약을 모두 확인합니다.")
    @GetMapping(admin)
    public List<ReservationDtoResponse> getReservationAll() {
        return reservationService.findAll();
    }
    @Operation(summary = "예약 상세 보기",description = "예약 상세내용을 확인합니다.")
    @GetMapping(admin+"/{id}")
    public ReservationDtoResponse.RVDetails myReservationInfoForAdmin(@PathVariable("id") String reservationId) {
        return reservationService.findMyReservationDetail(reservationId);
    }
    @Operation(summary = "예약 허가",description = "예약을 허가합니다 status B -> Y")
    @PatchMapping(admin+"/{id}")
    public void reservationConfirm(@PathVariable("id") String reservationId) {
        reservationService.confirm(reservationId);
    }
    @Operation(summary = "예약 반려",description = "예약을 반려합니다 status B -> N <br/> reason: 반려사유 작성")
    @PutMapping(admin+"/{id}")
    public void reservationReject(@PathVariable("id") String reservationId, @RequestBody ReservationRejectionReq reservationRejectionReq) {
        reservationService.reject(reservationId,reservationRejectionReq);
    }

    @Operation(summary = "투어 추가옵션 가격설정")
    @PostMapping(admin+"/tour/option/{id}")
    public void reservationTourOptionSetPrice(@PathVariable("id") Long optionId, BigDecimal price) {
        reservationService.setTourOptionPrice(
                optionId,price);
    }

}