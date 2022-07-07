package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.domain.entity.CustomTravelBoard;
import com.healthtrip.travelcare.domain.entity.ReservationInfo;
import com.healthtrip.travelcare.repository.CustomTravelBoardRepository;
import com.healthtrip.travelcare.repository.ReservationInfoRepository;
import com.healthtrip.travelcare.repository.dto.request.CustomTravelRequest;
import com.healthtrip.travelcare.repository.dto.response.CustomTravelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomTravelBoardService {

    private final CustomTravelBoardRepository customTravelRepository;
    private final ReservationInfoRepository reservationInfoRepository;

    @Transactional
    public ResponseEntity<Boolean> reserveCustom(CustomTravelRequest request) {

        // 커스텀 여행 등록을 위해 연결된 패키지 예약을 가져옴
//        var reservationInfo = reservationInfoRepository.getById(request.getReservationId());
        // reservationInfo 사용 주의 custom 불러올시 Lazy로 다시한번 db요청함 필요하면 EntityGraph 이용
        var reservationInfo= reservationInfoRepository.getByIdAndAccountId(request.getReservationId(), CommonUtils.getAuthenticatedUserId());
        if (reservationInfo == null){
            return ResponseEntity.ok(false);
        }
        // 커스텀 여행 객체 생성
        CustomTravelBoard customTravelBoard = CustomTravelBoard.builder()
                .title(request.getTitle())
                .reservationInfo(reservationInfo)
                .question(request.getQuestion())
                .build();
        // db에 저장
        CustomTravelBoard savedCustomTravel = customTravelRepository.save(customTravelBoard);
        if (savedCustomTravel.getId() == 0){
            // 저장 실패시
            return ResponseEntity.ok(false);
        }
        // 성공시 패키지 예약을 대기중으로 바꾼다.
        reservationInfo.customTravelOn();
        return ResponseEntity.ok(true);
    }

    @Transactional
    public ResponseEntity answer(CustomTravelRequest.Answer request) {
        // 선택된 커스텀 여행 문의글을 가져온다.
        Optional<CustomTravelBoard> optional= customTravelRepository.findById(request.getCustomTravelId());
        // 존재한다면
        if(optional.isPresent()){
            // 객체 받아서 답변 등록
            var customTravelBoard = optional.get();
            customTravelBoard.updateAnswer(request.getAnswer());
            var savedCustom = customTravelRepository.save(customTravelBoard);

            // 답변 등록 후 패키지 상태 업데이트= 대기중 -> 승인 or 거절
            savedCustom.getReservationInfo().customStatusUpdate(request.getAnswerStatus());
            return ResponseEntity.ok("답변완료");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomTravelResponse.Info>> myCustomRequests(Long reservationId) {
        // 예약 번호와 유저 번호로 하나의 예약정보 가져오기
        List<CustomTravelBoard> customTravelBoardList = customTravelRepository.findByReservationInfoIdAndUserId(reservationId, CommonUtils.getAuthenticatedUserId());
        if(customTravelBoardList.size() != 0){
            //있다면 예약정보-> 커스텀요청 목록-> DTO변환 -> List로 변경
            List<CustomTravelResponse.Info> responseBody = customTravelBoardList.stream().map(customTravelBoard ->
                        CustomTravelResponse.Info.builder()
                                .customReserveId(customTravelBoard.getId())

                                .title(customTravelBoard.getTitle())
                                .question(customTravelBoard.getQuestion())
                                .answer(customTravelBoard.getAnswer())
                                .build()
                    )
                    .collect(Collectors.toList());
            // 응답 200 OK
            return ResponseEntity.ok(responseBody);
        }else {
            // 없으면 200 OK null
            return ResponseEntity.status(200).body(null);
        }
    }
    @Transactional
    public ResponseEntity<Boolean> clientModify(CustomTravelRequest.ClientModify request) {
        // 커스텀 여행을 가져옴
        CustomTravelBoard customTravelBoard = customTravelRepository.findByIdAndAccountId(request.getCustomTravelId(),CommonUtils.getAuthenticatedUserId());
        if (customTravelBoard != null){
        // 이미 확정된 사항이라면 수정불가
            boolean statusCheck = customTravelBoard.getReservationInfo().getStatus() != ReservationInfo.Status.Y;
            if(statusCheck) {
        // 커스텀 여행 객체 변경
                customTravelBoard.updateClientRequest(request);
                customTravelRepository.save(customTravelBoard);
            }else {
                return ResponseEntity.ok(false);
            }
        }else{
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);

    }
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomTravelResponse.Info>> getReservationById(Long reservationId) {
        // 커스텀 예약 목록 조회
        List<CustomTravelBoard> myCustomReservations= customTravelRepository.findByReservationInfoId(reservationId);
        // 응답객체 생성
        List<CustomTravelResponse.Info> responseBody =
                myCustomReservations.stream().map(customTravelBoard ->
                        CustomTravelResponse.Info.builder()
                                .customReserveId(customTravelBoard.getId())

                                .title(customTravelBoard.getTitle())
                                .question(customTravelBoard.getQuestion())
                                .answer(customTravelBoard.getAnswer())
                                .build()
                ).collect(Collectors.toList());

        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CustomTravelResponse.Info> getCustomTravelDetails(Long customTravelId) {
        var option = customTravelRepository.findById(customTravelId);
        if (option.isPresent()){
         CustomTravelBoard customTravelBoard = option.get();
            CustomTravelResponse.Info responseBody = CustomTravelResponse.Info.builder()
                    .customReserveId(customTravelBoard.getId())

                    .title(customTravelBoard.getTitle())
                    .question(customTravelBoard.getQuestion())
                    .answer(customTravelBoard.getAnswer())
                    .build();
            return ResponseEntity.ok(responseBody);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
