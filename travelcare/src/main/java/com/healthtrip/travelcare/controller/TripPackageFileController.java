package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.TripPackageFileRequest;
import com.healthtrip.travelcare.repository.dto.response.TripPackageFileResponse;
import com.healthtrip.travelcare.service.TripPackageFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip-package/file")
@Tag(name = "여행패키지파일 API",description = "이미지파일 등")
public class TripPackageFileController {

    private final TripPackageFileService tripPackageFileService;

    // 패키지에서 이미지 가져오기
    @Operation(summary ="패키지 번호로 패키지에 포함된 이미지 파일들을 불러옴" )
    @GetMapping("/images")
    public ResponseEntity<List<TripPackageFileResponse.FileInfo>> getImages(@RequestParam Long tripPackageId) {
        var imageDtoList = tripPackageFileService.getData(tripPackageId);
        // 이후 문제시 분리
        return ResponseEntity.ok(imageDtoList);

    }

    // 이미지 추가 & 삭제로 패키지 이미지 수정!
    @Operation(summary = "패키지 번호로 해당 패키지 이미지 추가 *주의* 꼭 Schema 참고해주세요")
    @PostMapping("/images")
    public void addImage(@ModelAttribute TripPackageFileRequest request) {
        tripPackageFileService.addImage(request);
    }

    @Operation(summary = "파일 번호로 이미지 삭제")
    @DeleteMapping("/images")
    public void deleteImage(@RequestBody List<Long> fileIds) {
        tripPackageFileService.deleteImage(fileIds);
    }
}
