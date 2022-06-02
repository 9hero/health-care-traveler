package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.TripPackageFileRequest;
import com.healthtrip.travelcare.service.TripPackageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip-package-file")
public class TripPackageFileController {

    private final TripPackageFileService tripPackageFileService;

    // 패키지에서 이미지 가져오기
    @GetMapping("/images")
    public void getImages(@RequestParam Long tripPackageId) {
        tripPackageFileService.getData();
    }
    // 이미지 추가 & 삭제로 패키지 이미지 수정!
    @PostMapping("/images")
    public void a(@ModelAttribute TripPackageFileRequest request) {

    }

    // id로 조회, 삭제, 여러 데이터 받아서
    @DeleteMapping("/images")
    public void c() {

    }
}
