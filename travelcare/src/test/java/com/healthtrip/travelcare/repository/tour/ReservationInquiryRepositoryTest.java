package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.repository.reservation.ReservationInquiryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Disabled("불필요 기능")
class ReservationInquiryRepositoryTest {

    @Autowired
    ReservationInquiryRepository reservationInquiryRepository;

    @Test
    @Transactional
    void findByReservationInfoId() {
//        customTravelBoardRepository.findByReservationInfoIdAndUserId(59L,128L).stream().forEach(
//                System.out::println
//        );
//        var optional = customTravelBoardRepository.findById(52L);
//        var customTravelBoard = optional.get();
//        System.out.println("엉덩큐엘 나오나?");
//        System.out.println("user id = "+customTravelBoard.getReservationInfo().getAccount().getId());
//        System.out.println(customTravelBoardRepository.findByIdAndAccountId(53L,127L));
//        customTravelBoardRepository.findByReservationInfoId(23L).forEach(System.out::println);
    }
}