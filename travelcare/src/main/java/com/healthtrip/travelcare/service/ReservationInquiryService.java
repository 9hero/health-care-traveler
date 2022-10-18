package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.entity.reservation.ReservationInquiryChat;
import com.healthtrip.travelcare.entity.tour.reservation.ReservationInquiry;
import com.healthtrip.travelcare.repository.dto.response.ReservationInquiryChatResponse;
import com.healthtrip.travelcare.repository.reservation.ReservationInquiryChatRepo;
import com.healthtrip.travelcare.repository.reservation.ReservationRepository;
import com.healthtrip.travelcare.repository.reservation.ReservationInquiryRepository;
import com.healthtrip.travelcare.repository.dto.request.InquiryRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInquiryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationInquiryService {

    private final ReservationRepository reservationRepository;

    private final ReservationInquiryRepository reservationInquiryRepository;

    private final ReservationInquiryChatRepo chatRepository;
    @Transactional
    public ResponseEntity<Boolean> addInquiry(String id, InquiryRequest request) {

        // 예약 정보 가져오기
        // *)사용자 식별 필수 현재:제외
        var reservationInfo= reservationRepository.getByIdAndAccountId(id,CommonUtils.getAuthenticatedUserId());
        if (reservationInfo ==null){
            throw new CustomException("해당 예약 없음", HttpStatus.NOT_FOUND);
        }
        // 문의 객체 생성
        var reservationInquiry = ReservationInquiry.builder()
                .title(request.getTitle())
                .reservation(reservationInfo)
                .question(request.getQuestion())
                .build();
        // db에 저장
        ReservationInquiry savedCustomTravel = reservationInquiryRepository.save(reservationInquiry);
        if (savedCustomTravel.getId() == 0){
            // 저장 실패시
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }



    @Transactional(readOnly = true)
    public ReservationInquiryResponse.Info reservationInquiry(String reservationId) {
        // 예약 번호와 유저 번호로 하나의 예약정보 가져오기
        var inquiry= reservationInquiryRepository.getByReservationIdAndAccountId(reservationId, CommonUtils.getAuthenticatedUserId());
        return getInquiryResponseInfo(inquiry);
    }
    @Transactional(readOnly = true)
    public ReservationInquiryResponse.Info getReservationInquiryById(Long id) {
        // 문의 게시물 번호와 유저 번호로 하나의 예약정보 가져오기
        var inquiry= reservationInquiryRepository.getByIdAndAccountId(id, CommonUtils.getAuthenticatedUserId());
        return getInquiryResponseInfo(inquiry);
    }

    @Transactional(readOnly = true)
    public ReservationInquiryResponse.Info getReservationInquiryByIdForAdmin(Long id) {
        // 문의 게시물 번호로 찾기
        var inquiry= reservationInquiryRepository.getByIdForAdmin(id);
        return getInquiryResponseInfo(inquiry);
    }


    private ReservationInquiryResponse.Info getInquiryResponseInfo(ReservationInquiry inquiry) {
        if (inquiry != null){
            List<ReservationInquiryChatResponse> chatList = null;
            var chat= inquiry.getReservationInquiryChat();
            if (chat != null)  {
                chatList = inquiry.getReservationInquiryChat().stream().map(
                        ReservationInquiryChatResponse::toResponse
                ).collect(Collectors.toList());
            }
            return ReservationInquiryResponse.Info.builder()
                    .title(inquiry.getTitle())
                    .question(inquiry.getQuestion())
                    .chatList(chatList)
                    .writeAt(inquiry.getCreatedAt())
                    .updateAt(inquiry.getUpdatedAt())
                    .build();
        }else {
            throw new CustomException("해당 예약 문의 없음", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Boolean> clientModifyInquiry(Long id, InquiryRequest request) {
        // 문의 가져오기
        var inquiry= reservationInquiryRepository.getByIdAndAccountId(id, CommonUtils.getAuthenticatedUserId());
        if (inquiry != null){
            // 임시로그 나중에 db로 옮기던가 답변 받으면 수정 못하게
            log.info("inquiry change : {}",inquiry.getQuestion());
            inquiry.updateClientRequest(request);
            return ResponseEntity.ok(true);
        }else{
            throw new CustomException("해당 예약 문의 없음", HttpStatus.NOT_FOUND);
        }


    }

    @Transactional(readOnly = true)
    public List<ReservationInquiryResponse.InquiryList> myInquiries() {
        var reservationInquiryList = reservationInquiryRepository.findByAccountId(CommonUtils.getAuthenticatedUserId());
        return reservationInquiryList.stream().map(ReservationInquiryResponse.InquiryList::toResponse).collect(Collectors.toList());
    }


    @Transactional
    public void addComment(Long id, String chat) {
        var inquiry = reservationInquiryRepository.getByIdAndAccountId(id,CommonUtils.getAuthenticatedUserId());

        chatRepository.save(ReservationInquiryChat.builder()
                .chat(chat)
                .reservationInquiry(inquiry)
                .writer(ReservationInquiryChat.Writer.customer)
                .build());
    }




    @Transactional
    public boolean answer(Long id, String chat) {
        // 선택된 커스텀 여행 문의글을 가져온다.
        var inquiry=reservationInquiryRepository.getById(id);
        chatRepository.save(ReservationInquiryChat.builder()
                .chat(chat)
                .reservationInquiry(inquiry)
                .writer(ReservationInquiryChat.Writer.admin)
                .build());
        return true;
    }

    @Transactional(readOnly = true)
    public List<ReservationInquiryResponse.InquiryList> findAllForAdmin() {
        List<ReservationInquiry> reservationInquiryList = reservationInquiryRepository.findAllForAdmin();
        return reservationInquiryList.stream().map(ReservationInquiryResponse.InquiryList::toResponse).collect(Collectors.toList());
    }
}
