package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.CustomTravelBoard;
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

@RequiredArgsConstructor
@Service
public class CustomTravelBoardService {

    private final CustomTravelBoardRepository customTravelRepository;
    private final ReservationInfoRepository reservationInfoRepository;

    @Transactional
    public ResponseEntity<Boolean> reserveCustom(CustomTravelRequest request) {
        // 커스텀 여행 등록을 위해 연결된 패키지 예약을 가져옴
        var reservationInfo = reservationInfoRepository.getById(request.getReservationId());

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
    public ResponseEntity<List<CustomTravelResponse.Title>> myCustomReserve(Long reservationId) {
        // 커스텀 예약 목록 조회
        List<CustomTravelBoard> myCustomReservations= customTravelRepository.findByReservationInfoId(reservationId);
        // 응답객체 생성
        List<CustomTravelResponse.Title> responseBody = new ArrayList<>();
        myCustomReservations.forEach(customTravelBoard ->
            responseBody.add(
                    CustomTravelResponse.Title.builder()
                    .customReservationId(customTravelBoard.getId())  // 커스텀 예약 아이디
                    .title(customTravelBoard.getTitle()) // 제목
                    .build()
            )
        );

        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CustomTravelResponse.Info> getCustomTravelDetails(Long customTravelId) {
        var option = customTravelRepository.findById(customTravelId);
        CustomTravelResponse.Info responseBody = null;
        if (option.isPresent()){
         CustomTravelBoard customTravelBoard = option.get();
            responseBody = CustomTravelResponse.Info.builder()
                    .customReserveId(customTravelBoard.getId())
                    .ReservationInfoId(customTravelBoard.getReservationInfo().getId())
                    .title(customTravelBoard.getTitle())
                    .question(customTravelBoard.getQuestion())
                    .answer(customTravelBoard.getAnswer())
                    .build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        return ResponseEntity.ok(responseBody);
    }

    @Transactional
    public ResponseEntity clientModify(CustomTravelRequest.ClientModify request) {
        // 커스텀 여행을 가져옴
        var optional = customTravelRepository.findById(request.getCustomTravelId());

        // 커스텀 여행 객체 변경
        if (optional.isPresent()){
            CustomTravelBoard customTravelBoard = optional.get();
            customTravelBoard.updateClientRequest(request);
            customTravelRepository.save(customTravelBoard);
        }else{
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);

    }
}
