package com.healthtrip.travelcare.controller;


import com.healthtrip.travelcare.service.ReservationDateService;
import com.healthtrip.travelcare.service.ReservationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationDateService reservationDateService;
    private final ReservationInfoService reservationInfoService;


    // GET
    // 1. 모든 예약날짜 확인 x
    // 2. 특정 패키지 예약 가능일짜 확인
    // ?. 타입 별 패키지 검색

    // POST
    // 패키지 등록-> 패키지 날짜 추가 -> 패키지의 예약 날짜 입력 -> POST API


    @GetMapping("/date")
    public ResponseEntity packageDate(){

        return null;
    }

    @GetMapping("/info")
    public ResponseEntity myReservation(){
        return null;
    }




}
