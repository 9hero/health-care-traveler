package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.travel.PackageTourPayment;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class PackageTourPaymentRepositoryTest {

    @Autowired
    PackageTourPaymentRepository packageTourPaymentRepository;

    @Autowired
    ReservationInfoRepository reservationInfoRepository;

    @RepeatedTest(10)
    @Transactional
    @Rollback(value = true)
    void saveAndFind() {
        var a = reservationInfoRepository.getById("220724RV8077");

        var b = packageTourPayment();
        b.setReservationInfo(a);
        for (  int i = 0; i<4;i++){
            boolean conflict = packageTourPaymentRepository.existsById(b.idGenerate("TP"));
            if (!conflict) {
                packageTourPaymentRepository.save(b);
                break;
            }else {
                System.out.println("중복"+i);
                if(i == 3)return;
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