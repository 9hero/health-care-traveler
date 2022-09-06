package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.PackageTourPayment;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.tour.PackageTourPaymentRepository;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class PackageTourPaymentRepositoryTest {

    @Autowired
    PackageTourPaymentRepository packageTourPaymentRepository;

    private EntityProvider entityProvider;
    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }
    @Test
    @DisplayName("저장")
    void save() {
        // given
        PackageTourPayment packageTourPayment = entityProvider.getPackageTourPayment();
        packageTourPayment.getTourReservation().idGenerate();

        //when
        for (  int i = 0; i<4;i++){
            boolean conflict = packageTourPaymentRepository.existsById(packageTourPayment.idGenerate());
            if (!conflict) {
                packageTourPaymentRepository.save(packageTourPayment);
                break;
            }else {
                System.out.println("중복"+i);
                if(i == 3)return;
            }
        }

        // then
        assertThat(packageTourPayment.getId()).isNotBlank();
    }

    @Test
    @DisplayName("결제번호 중복테스트")
    void uniqueConflict() {
        // given
        // 기존 저장된 데이터로 가정
        PackageTourPayment oldPackageTourPayment = entityProvider.getPackageTourPayment();
        TourReservation tourReservation = oldPackageTourPayment.getTourReservation();
        oldPackageTourPayment.testIdGenerate();
        tourReservation.idGenerate();

        // 새로 등록할 데이터
        var newTourReservation=entityProvider.getNewTourResrvation();
        newTourReservation.TESTidGenerate();
        PackageTourPayment newPackageTourPayment = PackageTourPayment.builder()
                .tourReservation(newTourReservation)
                .amount(BigDecimal.valueOf(550L))
                .paymentDate(LocalDateTime.now())
                .currency("USD")
                .payType("CARD")
                .build();
        while (!oldPackageTourPayment.getId().equals(newPackageTourPayment.testIdGenerate())){
            System.out.println("gg");
        };

        //when
        packageTourPaymentRepository.save(oldPackageTourPayment);
        boolean conflict = packageTourPaymentRepository.existsById(newPackageTourPayment.getId());

        // then
        assertThat(conflict).isTrue();
    }

}