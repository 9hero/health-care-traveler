package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationDtoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import com.healthtrip.travelcare.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "예약 API")
public class ReservationController {
    private final String domain = "/reservation";
    private String admin = "/admin"+domain;
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
    @GetMapping(domain+"/details")
    public ReservationDtoResponse.RVDetails myReservationInfo(String reservationId) {
        return reservationService.findMyReservationDetail(reservationId);
    }

//    @Operation(summary = "예약인원정보 보기 (예약 번호 필요)",description = "예약 번호는 /api/reservation 에서 받아올 수 있습니다.")
//    @GetMapping(domain+"/{reservationId}")
//    public List<ReservationPersonResponse.rpInfo> reservationDetails(@PathVariable String reservationId){
//        return reservationService.getPeopleDataByReservationId(reservationId);
//    }
}
