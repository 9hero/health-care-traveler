package com.healthtrip.travelcare.entity;

import com.healthtrip.travelcare.entity.tour.reservation.CustomTravelBoard;
import com.healthtrip.travelcare.repository.CustomTravelBoardRepository;
import com.healthtrip.travelcare.repository.TourReservationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled
class CustomTravelBoardTest {

    @Autowired
    CustomTravelBoardRepository repository;

    @Autowired
    TourReservationRepository tourReservationRepository;

    @Test
    @Transactional
    void saveAndFind(){
        CustomTravelBoard customTravelBoard = CustomTravelBoard.builder()
                .answer("처리해드렸습니다")
                .title("저만의 여행 가고싶어요")
                .question("뇌병원 방문하고 서핑을 즐기고싶네요 어떻게해야하죠?")
                .tourReservation(tourReservationRepository.getById("9"))
                .build();

        repository.save(customTravelBoard);

    }
}