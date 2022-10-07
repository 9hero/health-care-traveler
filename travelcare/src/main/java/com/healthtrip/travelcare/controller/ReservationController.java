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
    private final String admin = "/admin"+domain;
    private final ReservationService reservationService;

    @Operation(summary = "통합 예약",description = "투어와 검진 동시에 예약합니다.")
    @PostMapping(domain)
    public void a(@RequestBody ReservationRequest.Integration reservationDTO) {
        reservationService.integrationReserve(reservationDTO);
    }
    @Operation(summary = "내 예약 보기",description = "전체 예약을 확인합니다.")
    @GetMapping(domain)
    public List<ReservationDtoResponse> myReservation(@Parameter(description = "Sort 객체 없이 보내주세요 <br> page: 현재 페이지, size: 페이지 당 객체 수 <br> /api/reservation?page=0&size=1")
            @PageableDefault(size = 10) Pageable pageable ) {
        return reservationService.findMyReservation(pageable);
    }
    @Operation(summary = "예약 상세 보기",description = "예약 상세내용을 확인합니다.")
    @GetMapping(domain+"/{id}")
    public ReservationDtoResponse.RVDetails myReservationInfo(@PathVariable("id") String reservationId) {
        return reservationService.findMyReservationDetail(reservationId);
    }


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
    public void reservationTourOptionSetPrice(@PathVariable("id") Long id, BigDecimal price) {
        reservationService.setTourOptionPrice(id,price);
    }

}