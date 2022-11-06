package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.ReservationPayment;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
@Disabled("예약 date 삭제전")
class ReservationPaymentRepositoryTest {

    @Autowired
    ReservationPaymentRepository reservationPaymentRepository;

    private EntityProvider entityProvider;
    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }
    @Test
    @DisplayName("저장")
    void save() {
        // given
        ReservationPayment reservationPayment = entityProvider.getReservationPayment();
//        reservationPayment.getTourReservation().idGenerate();

        //when
        for (  int i = 0; i<4;i++){
            boolean conflict = reservationPaymentRepository.existsById(reservationPayment.idGenerate());
            if (!conflict) {
                reservationPaymentRepository.save(reservationPayment);
                break;
            }else {
                System.out.println("중복"+i);
                if(i == 3)return;
            }
        }

        // then
        assertThat(reservationPayment.getId()).isNotBlank();
    }

    @Test
    @DisplayName("결제번호 중복테스트")
    void uniqueConflict() {
        // given
        // 기존 저장된 데이터로 가정
        ReservationPayment oldReservationPayment = entityProvider.getReservationPayment();
        var tourReservation = oldReservationPayment.getReservation();
        oldReservationPayment.testIdGenerate();
//        tourReservation.idGenerate();

        // 새로 등록할 데이터
        var newTourReservation=entityProvider.getReservation();
//        newTourReservation.TESTidGenerate();
        ReservationPayment newReservationPayment = ReservationPayment.builder()
                .reservation(newTourReservation)
                .amount(BigDecimal.valueOf(550L))
                .paymentDate(LocalDateTime.now())
                .currency("USD")
                .payType("CARD")
                .build();
        while (!oldReservationPayment.getId().equals(newReservationPayment.testIdGenerate())){
            System.out.println("gg");
        };

        //when
        reservationPaymentRepository.save(oldReservationPayment);
        boolean conflict = reservationPaymentRepository.existsById(newReservationPayment.getId());

        // then
        assertThat(conflict).isTrue();
    }

}