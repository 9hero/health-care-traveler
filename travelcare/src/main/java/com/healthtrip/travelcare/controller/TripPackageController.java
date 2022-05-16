package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TripPackageResponse;
import com.healthtrip.travelcare.service.TripPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip-package")
@RequiredArgsConstructor
@Tag(name = "여행 패키지 API")
public class TripPackageController {

    private final TripPackageService tpService;

    @Operation(summary = "메인 화면을 위한 여행 패키지 리스트를 불러옵니다")
    @GetMapping("")
    public List<TripPackageResponse.MainPagePack> allTripPack(){
        var tpList = tpService.allTripPack();
        return tpList;
    }

    @Operation(summary = "여행 패키지의 정보를 가져옵니다.",description = "패키지 기본 정보, 추가 이미지들,예약 가능한 날짜, 현재정원/최대정원")
    @GetMapping("/{id}")
    public TripPackageResponse.TripPackInfo tripPackInfo(@PathVariable("id") Long id) {
        var tpResponseDto = tpService.oneTripPack(id);
        return tpResponseDto;
    }

    @Operation(summary = "(현재 비활성)패키지를 등록합니다.",description = "현재 사용하지 않습니다.")
    @PostMapping("/add") // 테스트 전
    public ResponseEntity addTripPack(@RequestBody TripPackageRequestDto tripPackageRequestDto,
                                      @RequestHeader(name = "role") String role){
        if(role.equals("ROLE_ADMIN")){ // 후에 JWT로 변경 예정, user와 단방향 설정됨 trippack -> account
            return tpService.addTripPack(tripPackageRequestDto);
        }else {
            return ResponseEntity.status(403).body("관리자가 아닙니다.");
        }
    }
}
