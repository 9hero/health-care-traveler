package com.healthtrip.travelcare.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ReservationInfoRepositoryTest {

    @Autowired
    ReservationInfoRepository reservationInfoRepository;

    @Test
    @Transactional
    @Disabled
    void findByUserId() {
        //        예약자, 패키지명, 예약상태, 출발일, 도착일
        reservationInfoRepository.findByAccountId(70L).forEach(
                reservationInfo -> {
                    var date = reservationInfo.getReservationDate();

                    var tripPackage = date.getTripPackage();
                    System.out.println(reservationInfo.getReservationPerson());
                    System.out.println(tripPackage.getTitle());
                    System.out.println(reservationInfo.getStatus());
                    System.out.println(date.getDepartAt() +" ~ "+date.getArriveAt());
                }
        );
    }
    @Test
    @Transactional
    @Disabled
    void findByUserIdNative() {
        //        예약자, 패키지명, 예약상태, 출발일, 도착일
       var map = reservationInfoRepository.findMyReservation(70L);
        System.out.println(map.entrySet());
    }

}