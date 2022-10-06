package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.CategorySearchRequest;
import com.healthtrip.travelcare.repository.dto.response.HospitalResponse;
import com.healthtrip.travelcare.repository.dto.response.MedicalCheckProgramRes;
import com.healthtrip.travelcare.repository.dto.response.MedicalCheckupCategoryRes;
import com.healthtrip.travelcare.repository.dto.response.MedicalCheckupOptionalRes;
import com.healthtrip.travelcare.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "병원 API")
public class HospitalController {

    private final String domain = "/hospitals";
    private final String admin = "/admin"+domain;
    private final HospitalService hospitalService;

    @GetMapping(domain)
    @Operation(summary = "병원 목록 불러오기 id,name")
    public List<HospitalResponse> getHospitals() {
        return hospitalService.getHospitals();
    }
    @GetMapping(domain+"/{id}/MedicalCheckup/programs")
    @Operation(summary = "병원 검진 프로그램 목록 불러오기",description = "Query Parameter입니다.")
    public List<MedicalCheckProgramRes> findMedicalCheckProgramByHospital(
            @Parameter(name = "페이지 요청",description = "기본 설정: 0page 10size",example = "{\"page\":0,\"size\":10}") @PageableDefault Pageable pageable
            , @PathVariable(name = "id") Long hospitalId) {
        return hospitalService.getProgramsByHospital(hospitalId,pageable);
    }
    @GetMapping(domain+"/MedicalCheckup/categories")
    @Operation(summary = "검진 카테고리 모두 불러오기")
    public List<MedicalCheckupCategoryRes> findHeathCheckProgramByHospital() {
        return hospitalService.getCheckupCategories();
    }

    @PostMapping(domain+"/MedicalCheckup/categories/programs")
    @Operation(summary = "검진 카테고리로 검진 프로그램 검색")
    public List<MedicalCheckProgramRes> findWithCategoryIds(@RequestBody CategorySearchRequest categorySearchRequest) {
        return hospitalService.getProgramsByCategories(categorySearchRequest);
    }
    @Operation(summary = "프로그램 상세정보")
    @GetMapping(domain+"/MedicalCheckup/programs/{id}")
    public MedicalCheckProgramRes.MCPdetails programDetail(@PathVariable(name = "id") Long programId) {
        return hospitalService.getProgramDetails(programId);
    }

    @Operation(summary = "병원의 선택검사 모두 불러오기")
    @GetMapping(domain+"/{id}/MedicalCheckup/options")
    public List<MedicalCheckupOptionalRes> findMedicalCheckupOptions(@PathVariable(name = "id") Long hospitalId) {
        return hospitalService.getMedicalCheckupOptions(hospitalId);
    }

    /* 어드민 API */
    @Operation(summary = "프로그램 상세정보 조회(관리자)")
    @GetMapping(admin+"/MedicalCheckup/programs/{id}")
    public MedicalCheckProgramRes.MCPdetailsAdmin programDetailAdmin(@PathVariable(name = "id") Long programId) {
        return hospitalService.getProgramDetailsForAdmin(programId);
    }
    @Operation(summary = "프로그램 추가")
    @PostMapping(admin+"/{id}/MedicalCheckup/programs")
    public void addProgram(@PathVariable(name = "id") Long hospitalId) {
        hospitalService.addProgram(hospitalId);
    }
    @Operation(summary = "프로그램 범주 추가")
    @PutMapping(admin+"/MedicalCheckup/programs/{programId}")
    public void modifyProgramCategory(
                                      @PathVariable(name = "programId") Long programId,
                                      @RequestBody CategorySearchRequest.ModifyProgramCategory categorySearchRequest
    ) {
        hospitalService.modifyProgramCategory(programId,categorySearchRequest);
    }

}
