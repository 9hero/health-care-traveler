package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.entity.account.Tendency;
import com.healthtrip.travelcare.repository.dto.request.TendencyRequest;
import com.healthtrip.travelcare.repository.dto.response.TendencyResponse;
import com.healthtrip.travelcare.service.TendencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "심리 유형 API")
public class TendencyController {

    private final TendencyService tendencyService;

    @Operation(summary = "모두조회")
    @GetMapping("/tendency")
    public List<TendencyResponse> findAll() {
        return tendencyService.findAll();
    }

    @Operation(summary = "성향추가")
    @PostMapping("/admin/tendency")
    public void addTendency(TendencyRequest tendencyRequest) {
        tendencyService.addTendency(tendencyRequest);
    }
}
