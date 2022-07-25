package com.healthtrip.travelcare.controller;


import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import com.healthtrip.travelcare.service.ReservationDateService;
import com.healthtrip.travelcare.service.ReservationInfoService;
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


@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@Tag(name = "예약 API",description = "예약 정보,예약 일짜: 로그인 필요=Authorization:Bearer 'jwt'")
public class ReservationController {

    private final ReservationDateService reservationDateService;
    private final ReservationInfoService reservationInfoService;


    /*
        기관용/개인용/관리자용

    1. 예약 등록 / 예약 취소 O/O
    2. 내 예약 조회 / 수정 O/X
    3. 예약 허가 <- 관리자 controller X

    기관과 관리자
    기관: 내 예약목록(간략 목록) -> 하나 눌러서 상세정보 보기 -> 작업
    관리자: 모든 사용자 예약목록보기
     */
    // 마이 페이지 -> 내 예약목록 -> 특정 예약 상세 조회 -> 정보표기,취소,수정
    // 내 예약목록 도메인 정해야함 (유저 or 예약)

    //여기서 모든 내 예약정보 끌어옴
    @ApiResponse(responseCode = "200",description = "예약 정보 없을 시 null")
    @Operation(summary = "나의 예약목록",description = " 예약번호, 예약자, 패키지명, 예약상태, 출발일, 도착일을 가져옵니다. 필요한 정보가 있을 시 말해주세요.")
    @GetMapping("/info/me")// ㅇ
    public ResponseEntity<List<ReservationInfoResponse.MyInfo>> myReservation(){
        return reservationInfoService.myReservation();
    }

    @Operation(summary = "예약인원정보 보기 (예약 번호 필요)",description = "예약 번호는 /api/reservation/info/me 에서 받아올 수 있습니다.")
    @GetMapping("/info/{reservationId}")
    public ResponseEntity<List<ReservationPersonResponse.rpInfo>> reservationDetails(@PathVariable Long reservationId){
        return reservationInfoService.getPeopleDataByInfoId(reservationId);
    }

    // 패키지 등록-> 패키지 날짜 추가 -> 패키지의 예약 날짜 입력 -> POST API
    // 예약 상태 Y,N,B 있음 일반 패키지 예약에 대해서는 status를 뺄지 넣을지
    // 아니면 처음엔 무조건 Y(결제했으니까) 그 후에 custom 여행 신청시 status를 업데이트 B: 답변대기 Y:허가 N:거부
    @ApiResponses({ //***Throw로 처리예정***
            @ApiResponse(responseCode = "200", description = "등록성공",
            content = @Content(examples = @ExampleObject(value = "OK: 예약 등록 완료"))),
            @ApiResponse(responseCode = "400", description = "등록 실패 Error")
    })
    @Operation(summary = "예약하기",
    description = "AddressType: Single <br/>ROLE_COMMON: 본인 계정의 주소로 저장하기 때문에 주소 데이터는 필요없음 , ROLE_AGENT: 입력한 하나의 주소값이 모든 예약자의 주소로 저장됨" +
            "<br/> AddressType: ForEach = 주소 데이터 개별 입력")
    @PostMapping("/info")
    public ResponseEntity reservePackage(@RequestBody ReservationRequest.ReserveData reserveData) {
        return reservationInfoService.reserveTripPackage(reserveData);
    }

    @Operation(summary = "(임시)예약취소",description = "예약취소기록 남기기(현재 임시:처리 로직없이 삭제만 함),해당하는 예약id를 입력해주세요 현재 삭제할 시 예약자와 함께 삭제됩니다.")
    @DeleteMapping("/info/{reservationId}")
    public ResponseEntity cancelReservation(@PathVariable String reservationId) {
        return reservationInfoService.cancelReservation(reservationId);
    }

    // 내 예약수정 == 예약자 정보 변경
    @Operation(hidden = true,summary = "(비활성)예약 수정하기: 수정항목이 필요해요(예약 인원 변경 등)",description = "해당하는 예약번호를 입력해주세요")
    @PutMapping("/info/{reservationId}")
    public ResponseEntity modifyReservation() {
        return null;
    }


/*
     ---관리자용----
    @Operation(summary = "예약 조회")
    @GetMapping("/info/{reservationId}")
    public ResponseEntity reservation(@PathVariable Long reservationId){
        return null;
    }
    @Operation(summary = "(관리자용 현재 비활성)모든 패키지 예약 조회",description = "패키지 예약을 조회합니다. ")
    @GetMapping("/info")
    public ResponseEntity allReservation(){
        return null;
    }

    //----------------

    // ---예정 API---
    @Operation(summary = "(비활성)모든 패키지의 여행일자 확인")
    @GetMapping("/date")
    public ResponseEntity AllPackageDate(){
        return null;
    }
    // 상세 예약보기 (새로운 예약 추가시 분리 ex) 병원, 비행기 예약)

 */

}
