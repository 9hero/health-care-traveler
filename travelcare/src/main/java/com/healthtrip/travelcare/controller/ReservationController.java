package com.healthtrip.travelcare.controller;


import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import com.healthtrip.travelcare.service.ReservationDateService;
import com.healthtrip.travelcare.service.ReservationInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@RestController
@Tag(name = "예약 API",description = "예약 정보,예약 일짜")
public class ReservationController {

    private final ReservationDateService reservationDateService;
    private final ReservationInfoService reservationInfoService;


    /*
    예약 필요항목

    request body
    국가 id,
    주소: 주소1, 주소2, 지역, 도시, 우편번호
    예약자: 성,이름,성별,생년월일,연락처,비상 연락처
    예약정보: 유저 id, 예약날짜 id, 인원수, 예약상태
    예약상태 N default

    Repository
    Resv_Info
    Resv_date

        기관용/개인용/관리자용

    1. 예약 등록 / 예약 취소
    2. 내 예약 조회 / 수정
    3. 커스텀 여행 등록/조회/수정/취소
    4. 예약 허가 <- 관리자 controller

    기관과 관리자
    기관: 내 예약목록(간략 목록) -> 하나 눌러서 상세정보 보기 -> 작업
    관리자: 모든 사용자 예약목록보기
     */
    // 마이 페이지 -> 내 예약목록 -> 특정 예약 상세 조회 -> 정보표기,취소,수정
    // 내 예약목록 도메인 정해야함 (유저 or 예약)

    //여기서 모든 내 예약정보 끌어옴
    @Operation(summary = "특정 유저의 예약목록",description = " 예약자, 패키지명, 예약상태, 출발일, 도착일을 가져옵니다. 필요한 정보가 있을 시 말해주세요. test= id:70 사용해주세요")
    @GetMapping("/user/{userId}")// ㅇ
    public ResponseEntity<List<ReservationInfoResponse.MyInfo>> myReservation(@PathVariable Long userId){
        return reservationInfoService.myReservation(userId);
    }

    // 패키지 등록-> 패키지 날짜 추가 -> 패키지의 예약 날짜 입력 -> POST API
    @Operation(summary = "예약하기(예약시 한명만 가능 가족단위 예약시 변경필요)")
    @PostMapping("/info")// ㅇ
    public ResponseEntity reservePackage(@RequestBody ReservationRequest.ReserveData reserveData) {
        return reservationInfoService.reservePackage(reserveData);

    }

    @Operation(summary = "(임시)예약취소",description = "(임시:처리 로직없이 삭제만 함),해당하는 예약id를 입력해주세요 예약자와 함께 삭제됩니다., 예약취소로그 테이블만드는거 어떤가요?")
    @DeleteMapping("/info/{reservationId}")
    public ResponseEntity cancelReservation(@PathVariable Long reservationId) {
        return reservationInfoService.cancelReservation(reservationId);
    }

    // 내 예약수정 == 예약자 정보 변경
    @Operation(summary = "(비활성)예약 수정하기: 수정항목이 필요해요",description = "해당하는 예약id를 입력해주세요 부분수정 x 덮어씌우기 o 부분수정을 원하신다면 수정항목을 알려주세요")
    @PutMapping("/info/{reservationId}")
    public ResponseEntity modifyReservation() {
        return null;
    }


    // ---커스텀여행---
    @Operation(summary = "커스텀 여행 등록")
    @PostMapping("/custom")
    public ResponseEntity reserveCustom() {
        return null;
    }

    @Operation(summary = "커스텀 여행 조회")
    @GetMapping("/custom")
    public ResponseEntity myCustom() {
        return null;
    }

    @Operation(summary = "커스텀 여행 수정")
    @PutMapping("/custom")
    public ResponseEntity modifyCustom() {
        return null;
    }
    @Operation(summary = "커스텀 여행 삭제")
    @DeleteMapping("/custom")
    public ResponseEntity deleteCustom() {
        return null;
    }
    //---------------

    // ---관리자용----
//    @Operation(summary = "예약 조회")
//    @GetMapping("/info/{reservationId}")
//    public ResponseEntity reservation(@PathVariable Long reservationId){
//        return null;
//    }
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
    @Operation(summary = "(비활성)예약 상세 보기",description = "필요한 데이터 상의바람")
    @GetMapping("/info/{reservationId}")
    public ResponseEntity reservationDetails(@PathVariable Long reservationId){
        return null;
    }

}
