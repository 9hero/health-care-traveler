package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.dto.response.CustomTravelResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@SpringBootTest
class ReservationInfoRepositoryTest {
@Autowired
TourPackageDateRepository tourPackageDateRepository;
    @Autowired
    TourReservationRepository tourReservationRepository;
    @Autowired
    AccountsRepository accountsRepository;

    TourReservation entity() {
        return TourReservation.builder()
//                .id(CommonUtils.dateWithTypeIdGenerate("RV"))
                .account(accountsRepository.getById(128L))
                .tourPackageDate(tourPackageDateRepository.getById(30L))
                .personCount((short) 1)
//                .reservationPerson()
                .status(TourReservation.Status.Y)
                .paymentStatus(TourReservation.PaymentStatus.N)
                .csStatus(TourReservation.CsStatus.K)
                .amount(BigDecimal.valueOf(567L))
                .build();
    }
    @DisplayName("주문코드 중복테스트")
    @RepeatedTest(10)
    @Transactional
    @Rollback(value = true)
    void uniqueConflict() {
        var b = entity();

        for (int i = 0; i<4;i++){
            boolean conflict = tourReservationRepository.existsById(b.TESTidGenerate("RV"));
            if (!conflict) {
                tourReservationRepository.save(b);
                break;
            }else {
                System.out.println("중복"+i);
                if(i == 3)return;
            }
        }
                tourReservationRepository.flush();
        System.out.println(tourReservationRepository.findById(b.getId()).get());
    }

    @Test
    @Transactional
    @Disabled
    void findByUserId() {
        //        예약자, 패키지명, 예약상태, 출발일, 도착일
        tourReservationRepository.findByAccountId(70L).forEach(
                reservationInfo -> {
                    var date = reservationInfo.getTourPackageDate();

                    var tripPackage = date.getTourPackage();
                    System.out.println(reservationInfo.getTourReservationPeople());
                    System.out.println(tripPackage.getTitle());
                    System.out.println(reservationInfo.getStatus());
                    System.out.println(date.getDepartAt() +" ~ "+date.getArriveAt());
                }
        );
    }
    @Test
    @Transactional
//    @Disabled
    void findByUserIdNative() {
//        System.out.println(
//                reservationInfoRepository.findByAccountId(128L).get(0).getAccount().getId()
//        reservationInfoRepository.getByIdAndAccountId(59L,128L).getCustomTravelBoard()
//        );
        tourReservationRepository.getByIdAndAccountId("59",128L).getCustomTravelBoard().stream().map(
                customTravelBoard -> CustomTravelResponse.Info.builder()
                        .customReserveId(customTravelBoard.getId())
                        .title(customTravelBoard.getTitle())
                        .question(customTravelBoard.getQuestion())
                        .answer(customTravelBoard.getAnswer())
                        .build()
        ).collect(Collectors.toList()).forEach(System.out::println);
        //        예약자, 패키지명, 예약상태, 출발일, 도착일
//       var map = reservationInfoRepository.findMyReservation(70L);
//        System.out.println(map.entrySet());

    }


}