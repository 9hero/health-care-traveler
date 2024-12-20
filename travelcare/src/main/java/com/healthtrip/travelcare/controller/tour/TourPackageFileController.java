package com.healthtrip.travelcare.controller.tour;

import com.healthtrip.travelcare.repository.dto.request.TourPackageFileRequest;
import com.healthtrip.travelcare.repository.dto.response.TourPackageFileResponse;
import com.healthtrip.travelcare.service.TourPackageFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "투어패키지파일 API",description = "이미지파일 등")
public class TourPackageFileController {
    private final String domain = "/tour/package/file";
    private final String targetDomain = "/tour/package/{id}/file";
    private final String adminApi = "/admin"+domain;
    private final String targetAdminApi = "/admin"+targetDomain;


    private final TourPackageFileService tourPackageFileService;

    // 패키지에서 이미지 가져오기
    @Tag(name = "투어 패키지 API")
    @Operation(summary ="패키지 번호로 패키지에 포함된 이미지 파일들을 불러옴" )
    @GetMapping("/tour/package/{id}/file/images")
    public ResponseEntity<List<TourPackageFileResponse>> getImages(@PathVariable(name = "id") Long tourPackageId) {
        var imageDtoList = tourPackageFileService.getImages(tourPackageId);
        // 이후 문제시 분리
        return ResponseEntity.ok(imageDtoList);
    }

    // 이미지 추가 & 삭제로 패키지 이미지 수정!
    @Operation(summary = "패키지 번호로 해당 패키지 이미지 추가")
    @PostMapping(targetAdminApi+"/images")
    public void addImage(@PathVariable(name = "id") Long tourPackageId,
                         TourPackageFileRequest request) {
        tourPackageFileService.addImage(tourPackageId,request);
    }

    @Operation(summary = "파일 이미지 번호로 이미지 삭제")
    @DeleteMapping(adminApi+"/images")
    public void deleteImage(@RequestBody List<Long> fileIds) {
        tourPackageFileService.deleteImage(fileIds);
    }


}
