package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.ReservationDateRequest;
import com.healthtrip.travelcare.service.ReservationDateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation/date")
@Tag(name = "여행일 API")
public class TourPackageDateController {

    private final ReservationDateService reservationDateService;

    /*
    @Operation(summary = "패키지의 여행일자를 불러옵니다 (id)")
    @GetMapping("")
    public ResponseEntity getTripDates(@RequestParam Long id) {

    }
     */
    @Operation(summary = "패키지의 여행일자를 작성하여 추가합니다. (tripPackageId)")
    @PostMapping("")
    public ResponseEntity addTripDates(@RequestBody ReservationDateRequest.AddTrip request) {
        return ResponseEntity.ok(reservationDateService.addTripDate(request));
    }

    @Operation(summary = "최대인원, 현재인원, 출발시각,도착시각을 수정합니다.(덮어씌우기) ReservationDateId")
    @PutMapping("")
    public ResponseEntity modifyTripDates(@RequestBody ReservationDateRequest.Modify request) {
        return ResponseEntity.ok(reservationDateService.modifyTripDates(request));
    }
}
