package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.CustomTravelRequest;
import com.healthtrip.travelcare.repository.dto.response.CustomTravelResponse;
import com.healthtrip.travelcare.service.CustomTravelBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/custom")
@Tag(name = "커스텀 여행 문의 API")
public class CustomTravelController {

    private final CustomTravelBoardService customTravelService;

    // ---커스텀여행--- 어쩌면 status 필요할수도
    // 현재 테이블은 한 예약에 여러개의 커스텀 여행을 등록할수있음.
    // 1:1 관계로 해야할수도
    @Operation(summary = "커스텀 여행 등록")
    @PostMapping("")
    public ResponseEntity reserveCustom(@RequestBody CustomTravelRequest request) {
        return customTravelService.reserveCustom(request);
    }

    // 현재는 글에서 하나의 답변을 주는식임 새로운 방법필요
    // 1. 기존 게시판에서 status 추가해서 답변전,답변후,새로운답변 상태를 추가 그래서 알림으로 제공 알림제공시:
    // 문자나 이메일로 1:1 관리
    @Operation(summary = "커스텀 답변 및 수정")
    @PatchMapping("")
    public ResponseEntity answer(@RequestBody CustomTravelRequest.Answer request) {
        return customTravelService.answer(request);
    }


    @Operation(summary = "예약 id로 등록된 커스텀 여행을 모두 조회",description = "제목과 커스텀 예약id를 보내줍니다. 사용법: 예약목록에서 각 예약 마다 커스텀여행불러오기 버튼")
    @GetMapping("")
    // 1. 유저 id로 조회 홈페이지에서 조회
    // 2. 예약 id로 조회 마이페이지에서 조회 이거랑
    // 3. 커스텀 여행 id로 조회 마이페이지에서 조회 이거로 결정
    public ResponseEntity<List<CustomTravelResponse.Title>> getCustomReserve(@RequestParam Long reservationId) {
        return customTravelService.myCustomReserve(reservationId);
    }
        // 이거 위아래 하나로 합치삼 한 예약 id에 대해서 배열로 [커스텀여행 문의사항 상세조회] 가져오기
    @Operation(summary = "해당 커스텀 여행 문의 상세사항 조회", description = "커스텀 여행 id,제목,답변, 패키지 예약 id,(필요시: 등록일,수정일) ")
    @GetMapping("/{customTravelId}")
    public ResponseEntity<CustomTravelResponse.Info> getCustomTravelDetails(@PathVariable Long customTravelId) {
        return customTravelService.getCustomTravelDetails(customTravelId);
    }

    @Operation(summary = "고객이 커스텀 여행 제목,질문사항 수정")
    @PutMapping("")
    public ResponseEntity modifyCustom(@RequestBody CustomTravelRequest.ClientModify request) {
        return customTravelService.clientModify(request);
    }

    @Operation(summary = "(비활성)커스텀 여행 삭제")
    @DeleteMapping("/{customTravelId}")
    public ResponseEntity deleteCustom(@PathVariable Long customTravelId) {
        return null;
    }
}
