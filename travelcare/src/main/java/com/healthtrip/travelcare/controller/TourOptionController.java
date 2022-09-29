package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.response.TourOptionsResponse;
import com.healthtrip.travelcare.service.TourOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "투어 패키지 API")
public class TourOptionController {

    private final String domain = "/tour/options";
    private final String adminApi = "/admin"+domain;

    private final TourOptionService tourOptionService;

    @GetMapping(domain)
    @Operation(summary = "투어 옵션을 모두 불러옵니다.")
    public List<TourOptionsResponse> getOptions(){
        return tourOptionService.getOptions();
    }
}
