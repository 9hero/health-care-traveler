package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.CustomTravelBoardRepository;
import com.healthtrip.travelcare.repository.ReservationInfoRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class CustomTravelBoardTest {

    @Autowired
    CustomTravelBoardRepository repository;

    @Autowired
    ReservationInfoRepository reservationInfoRepository;

    @Test
    @Transactional
    void saveAndFind(){
        CustomTravelBoard customTravelBoard = CustomTravelBoard.builder()
                .answer("처리해드렸습니다")
                .title("저만의 여행 가고싶어요")
                .question("뇌병원 방문하고 서핑을 즐기고싶네요 어떻게해야하죠?")
                .reservationInfo(reservationInfoRepository.getById(9L))
                .build();

        repository.save(customTravelBoard);

    }
}