package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.TourPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TourPackageResponse;
import com.healthtrip.travelcare.service.TourPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "투어 패키지 API")
public class TourPackageController {
    private final String domain = "/tour/package";
    private final String adminApi = "/admin"+domain;
    private final TourPackageService tpService;

    @SecurityRequirement(name = "no")
    @Operation(summary = "메인 화면을 위한 여행 패키지 리스트를 불러옵니다")
    @GetMapping(domain)
    public List<TourPackageResponse.TPBasicInfo> mainPageTourPackages(){
        var tpList = tpService.mainPagePackages();
        return tpList;
    }
    @SecurityRequirement(name = "no")
    @Operation(summary = "여행 패키지의 정보와 이미지들을 가져옵니다.",description = "패키지 기본 정보+메인 이미지+ 투어이미지")
    @GetMapping(domain+"/{id}")
    public TourPackageResponse.tourWithImages tourPackageInfo(@PathVariable("id") Long id) {
        return tpService.tourPackageDetails(id);
    }

    @Operation(summary = "패키지를 등록합니다.",description = "메인 이미지와 패키지 정보를 보내주세요")
    @PostMapping(adminApi)
    public ResponseEntity addTourPack(TourPackageRequestDto tourPackageRequestDto){
        try {
        return tpService.addTourPack(tourPackageRequestDto);
        }catch (RuntimeException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("오류 발생: "+e.getMessage());
        }
    }



}
