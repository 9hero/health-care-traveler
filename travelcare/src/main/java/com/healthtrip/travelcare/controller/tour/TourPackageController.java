package com.healthtrip.travelcare.controller.tour;

import com.healthtrip.travelcare.repository.dto.request.TourPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TourPackageResponse;
import com.healthtrip.travelcare.service.TourPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public List<TourPackageResponse.TPBasicInfo> mainPageTourPackages(@Parameter(description = "심리 유형 id(optional)") @RequestParam(required = false) Long tendencyId){
        var tpList = tpService.mainPagePackages(tendencyId);
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

    @Operation(summary = "패키지를 수정합니다.", description = "덮어씌우기 방식, 수정항목: 패키지명,소개,기본제공,불포함 사항, 주의 사항 <br/> 주의 put 방식이라 null 항목의 데이터는 삭제 됩니다.")
    @PutMapping(adminApi+"/{id}")
    public void modifyTourPackage(@PathVariable("id")Long tourPackageId,@RequestBody TourPackageRequestDto.UpdateTP tourPackageRequestDto) {
        tpService.modifyTourPackage(tourPackageId,tourPackageRequestDto);

    }

    @Operation(summary = "패키지에 심리 성향을 추가합니다.")
    @PostMapping(adminApi+"/{id}/tendency")
    public void addTourPackageTendency(@PathVariable("id")Long id,@RequestParam Long tendencyId){
        tpService.addTendency(id,tendencyId);
    }

}
