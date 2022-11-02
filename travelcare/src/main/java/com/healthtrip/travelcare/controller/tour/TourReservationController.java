package com.healthtrip.travelcare.controller.tour;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
@Tag(name = "예약 API")
public class TourReservationController {

    private final String domain = "/reservation/tour";
    private final String adminApi = "/admin"+domain;

}
