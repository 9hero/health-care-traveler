package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import com.healthtrip.travelcare.repository.dto.response.CustomTravelResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
ReservationDateRepository reservationDateRepository;
    @Autowired
    ReservationInfoRepository reservationInfoRepository;
    @Autowired
    AccountsRepository accountsRepository;

    ReservationInfo entity() {
        return ReservationInfo.builder()
//                .id(CommonUtils.dateWithTypeIdGenerate("RV"))
                .account(accountsRepository.getById(128L))
                .reservationDate(reservationDateRepository.getById(30L))
                .personCount((short) 1)
//                .reservationPerson()
                .status(ReservationInfo.Status.Y)
                .paymentStatus(ReservationInfo.PaymentStatus.N)
                .csStatus(ReservationInfo.CsStatus.K)
                .amount(BigDecimal.valueOf(567L))
                .build();
    }
    @DisplayName("주문코드 중복테스트")
    @Test()
    @Transactional
    @Rollback(value = true)
    void uniqueConflict() {
        var b = entity();

        for (int i = 0; i<3;i++){
            boolean conflict = reservationInfoRepository.existsById(b.idGenerate("RV"));
            if (!conflict) {
                reservationInfoRepository.save(b);
                break;
            }else {
                System.out.println("중복"+i);
                if(i == 3)return;
            }
        }
                reservationInfoRepository.flush();
        System.out.println(reservationInfoRepository.findById(b.getId()).get());
    }

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
//        System.out.println(
//                reservationInfoRepository.findByAccountId(128L).get(0).getAccount().getId()
//        reservationInfoRepository.getByIdAndAccountId(59L,128L).getCustomTravelBoard()
//        );
        reservationInfoRepository.getByIdAndAccountId("59",128L).getCustomTravelBoard().stream().map(
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