package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.InquiryRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInquiryResponse;
import com.healthtrip.travelcare.service.ReservationInquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "여행 문의 API")
public class QuestionAndAnswerController {
    private final String domain = "/reservation/inquiry";
    private final String targetDomain = "/reservation/{id}/inquiry";
    private final String adminApi = "/admin"+domain;
    private final ReservationInquiryService reservationInquiryService;

    @Operation(summary = "특정 예약에 대한 문의 등록")
    @PostMapping(targetDomain)
    public ResponseEntity<Boolean> reserveInquiry(@PathVariable(name = "id") String id,@RequestBody InquiryRequest request) {
        return reservationInquiryService.addInquiry(id,request);
    }

    @Operation(summary = "내 문의 모두 보기")
    @GetMapping(domain)
    public List<ReservationInquiryResponse.InquiryList> myInquiries() {
        return reservationInquiryService.myInquiries();
    }
    @Operation(summary = "(문의 게시글 id로 조회)특정 예약의 문의와 댓글 조회")
    @GetMapping(domain+"/{id}")
    public ReservationInquiryResponse.Info myInquiry(@PathVariable(name = "id") Long id) {
        return reservationInquiryService.getReservationInquiryById(id);
    }
    @Operation(summary = "(예약 id로 조회)특정 예약의 문의와 댓글 조회")
    @GetMapping(targetDomain)
    public ReservationInquiryResponse.Info myReservationsInquiry(@PathVariable(name = "id") String reservationId) {
        return reservationInquiryService.reservationInquiry(reservationId);
    }

    @Operation(summary = "고객 댓글 등록")
    @PostMapping(domain + "/{id}/comment")
    public void addComment(@PathVariable(name = "id") Long id,@RequestBody String chat) {
        reservationInquiryService.addComment(id,chat);
    }
    @Operation(summary = "고객 댓글 수정") // 본인 댓글인지 확인 필요
    @PutMapping(domain + "/comment/{id}")
    public void modifyComment(@PathVariable(name = "id") Long id,@RequestBody String chat) {
        reservationInquiryService.modifyComment(id,chat);
    }
    @Operation(summary = "문의글 제목,문의사항 수정")
    @PutMapping(domain+"/{id}")
    public ResponseEntity<Boolean> modifyCustom(@PathVariable(name = "id") Long id,@RequestBody InquiryRequest request) {
        return reservationInquiryService.clientModifyInquiry(id,request);
    }

    /*
            관리자 API
     */

    @Operation(summary = "해당 예약문의 답변")
    @PostMapping(adminApi+"/{id}")
    public boolean answer(@PathVariable(name = "id") Long id,@RequestBody String chat) {
        return reservationInquiryService.answer(id,chat);
    }

    @Operation(summary = "관리자 답변 수정")
    @PutMapping(adminApi+"/comment/{id}")
    public void modifyAnswer(@PathVariable(name = "id") Long id,@RequestBody String chat) {
        reservationInquiryService.modifyAnswer(id,chat);
    }

    @Operation(summary = "예약 모두 조회")
    @GetMapping(adminApi)
    public List<ReservationInquiryResponse.InquiryList> getCustomReserve() {
        return reservationInquiryService.findAllForAdmin();
    }

    @Operation(summary = "특정 예약의 문의와 댓글 조회")
    @GetMapping(adminApi+"/{id}")
    public ReservationInquiryResponse.Info getReservationInquiryByIdForAdmin(@PathVariable(name = "id") Long id) {
        return reservationInquiryService.getReservationInquiryByIdForAdmin(id);
    }
}
