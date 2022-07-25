package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.travel.PackageTourPayment;
import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PackageTourPaymentRepositoryTest {

    @Autowired
    PackageTourPaymentRepository packageTourPaymentRepository;

    @Autowired
    ReservationInfoRepository reservationInfoRepository;

    @Test
    @Transactional
    @Rollback(value = true)
    void saveAndFind() {
        var a = reservationInfoRepository.getById("220724RV8077");

        var b = packageTourPayment();
        b.setReservationInfo(a);
        for (  int i = 0; i<3;i++){
            boolean conflict = packageTourPaymentRepository.existsById(b.generateTourPaymentId());
            if (!conflict) {
                packageTourPaymentRepository.save(b);
                break;
            }else {
                //중복 발생시 추가 처리 없으면 다시 save하러감
                return;
            }
        }
        packageTourPaymentRepository.flush();
        System.out.println(packageTourPaymentRepository.findById(b.getId()).get());
    }

    PackageTourPayment packageTourPayment() {
        return PackageTourPayment.builder()
                .amount(BigDecimal.valueOf(550L))
                .paymentDate(LocalDateTime.now())
                .currency("USD")
                .payType("CARD")
                .build();
    }
}