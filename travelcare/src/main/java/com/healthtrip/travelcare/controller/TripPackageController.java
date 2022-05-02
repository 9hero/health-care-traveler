package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.service.TripPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/trip-packages")
@RequiredArgsConstructor
public class TripPackageController {

    private final TripPackageService tpService;

    @GetMapping("")
    public ResponseEntity allTripPack(){
        ResponseEntity tpList = tpService.allTripPack();
        return tpList;
    }

    @GetMapping("{id}")
    public ResponseEntity oneTripPack(@PathParam("id") Long id) {
        ResponseEntity tpResponseDto = tpService.oneTripPack(id);
        return tpResponseDto;
    }

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
