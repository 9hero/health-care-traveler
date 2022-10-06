package com.healthtrip.travelcare.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
@Tag(name = "예약 API")
public class TourReservationController {

    private final String domain = "/tour/reservation";
    private final String adminApi = "/admin"+domain;

}
