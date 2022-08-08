package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.CustomTravelRequest;
import com.healthtrip.travelcare.repository.dto.response.CustomTravelResponse;
import com.healthtrip.travelcare.service.CustomTravelBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "커스텀 여행 문의 API",description = "로그인 필요=Authorization:Bearer 'jwt'")
public class CustomTourController {
    private final String domain = "/tour/custom";
    private final String adminApi = "/admin"+domain;
    private final CustomTravelBoardService customTravelService;

    // ---커스텀여행--- 어쩌면 status 필요할수도
    // 현재 테이블은 한 예약에 여러개의 커스텀 여행을 등록할수있음.
    // 1:1 관계로 해야할수도
    @ApiResponses({
            @ApiResponse(responseCode = "Z00",description = "해당 예약 없음 or 등록실패",
                    content = @Content(examples = @ExampleObject(value = "false"))),
            @ApiResponse(responseCode = "200",description = "등록성공",
                    content = @Content(examples = @ExampleObject(value = "true")))
    })
    @Operation(summary = "커스텀 여행 등록")
    @PostMapping(domain)
    public ResponseEntity reserveCustom(@RequestBody CustomTravelRequest request) {
        return customTravelService.reserveCustom(request);
    }

    // 현재는 글에서 하나의 답변을 주는식임 새로운 방법필요
    // 1. 기존 게시판에서 status 추가해서 답변전,답변후,새로운답변 상태를 추가 그래서 알림으로 제공 알림제공시:
    // 문자나 이메일로 1:1 관리
    @Operation(summary = "(관리자)커스텀 답변 및 수정")
    @PatchMapping(adminApi)
    public ResponseEntity answer(@RequestBody CustomTravelRequest.Answer request) {
        return customTravelService.answer(request);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "Z00",description = "커스텀 예약 없음",
                    content = @Content(examples = @ExampleObject(value = "null"))),
            @ApiResponse(responseCode = "200",description = "조회성공")
    })
    @Operation(summary = "내 예약번호에 속한 커스텀 여행 요청들을 조회")
    @GetMapping(domain+"/me/{reservationId}")
    public ResponseEntity<List<CustomTravelResponse.Info>> myCustomRequests(@PathVariable Long reservationId) {
        return customTravelService.myCustomRequests(reservationId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "Z00",description = "확정된 커스텀여행 요청글 수정불가",
                    content = @Content(examples = @ExampleObject(value = "false"))),
            @ApiResponse(responseCode = "200",description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = "true")))
    })
    @Operation(summary = "고객이 커스텀 여행 제목,질문사항 수정")
    @PutMapping(domain)
    public ResponseEntity<Boolean> modifyCustom(@RequestBody CustomTravelRequest.ClientModify request) {
        return customTravelService.clientModify(request);
    }

    @Operation(hidden = true,summary = "(비활성)커스텀 여행 삭제")
    @DeleteMapping(domain+"/{customTravelId}")
    public ResponseEntity deleteCustom(@PathVariable Long customTravelId) {
        return null;
    }



    @Operation(summary = "(관리자)예약 id로 등록된 커스텀 여행을 모두 조회",description = "제목과 커스텀 예약id를 보내줍니다. 사용법: 예약목록에서 각 예약 마다 커스텀여행불러오기 버튼")
    @GetMapping(adminApi)
    // 1. 유저 id로 조회 홈페이지에서 조회
    // 2. 예약 id로 조회 마이페이지에서 조회 이거랑
    // 3. 커스텀 여행 id로 조회 마이페이지에서 조회 이거로 결정
    public ResponseEntity<List<CustomTravelResponse.Info>> getCustomReserve(@RequestParam Long reservationId) {
        return customTravelService.getReservationById(reservationId);
    }
    @Operation(summary = "(관리자)해당 커스텀 여행 문의 상세사항 조회", description = "커스텀 여행 id,제목,답변, 패키지 예약 id,(필요시: 등록일,수정일) ")
    @GetMapping(adminApi+"/{customTravelId}")
    public ResponseEntity<CustomTravelResponse.Info> getCustomTravelDetails(@PathVariable Long customTravelId) {
        return customTravelService.getCustomTravelDetails(customTravelId);
    }
}
