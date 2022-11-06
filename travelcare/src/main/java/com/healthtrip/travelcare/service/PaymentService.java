package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.entity.tour.ReservationPayment;
import com.healthtrip.travelcare.repository.reservation.ReservationRepository;
import com.healthtrip.travelcare.repository.tour.ReservationPaymentRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final IamportClient iamportClient;
    private final ReservationRepository reservationRepository;
    private final ReservationPaymentRepository reservationPaymentRepository;

    public String verify(String imp_uid, String merchant_uid) throws IamportResponseException, IOException {
        // 결제내역 조회
        IamportResponse<Payment> result = iamportClient.paymentByImpUid(imp_uid);
        Payment payment = result.getResponse();

        // 주문내역 조회
        var reservation = reservationRepository.findById(merchant_uid).orElseThrow(() -> {
            log.error("요청된 주문을 찾을 수 없습니다 Reservation Id: {} ",merchant_uid);
            throw new CustomException("주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        });
        // 실제 결제금액과 예약 상품 금액 비교
//            payment.getCurrency();
        var paidAmount = payment.getAmount(); // 고객의 총 결제 금액
        var tour = reservation.getTourReservation();
        var hospital = reservation.getHospitalReservation();

        // 비교
        var total = tour.getAmount().add(hospital.getAmount());
        int confirm = paidAmount.compareTo(total);

        // 성공
        if (confirm == 0) {
            // 결제내역 DB 저장
            ReservationPayment entity = ReservationPayment.builder()
                    .payType(payment.getPayMethod())
                    .currency(payment.getCurrency())
                    .paymentDate(LocalDateTime.ofInstant(payment.getPaidAt().toInstant(), ZoneId.systemDefault()))
                    .reservation(reservation)
                    .amount(payment.getAmount())
                    .build();
            boolean saved = (savePackageTourPayment(entity) != null);

            // 예약 결제완료
//                reservationInfo.paid();
            if (saved){
                reservation.paid();
                return "결제완료";
            }else {
                return "결제완료, 결제정보등록실패 코드와 함께 관리자에게 반드시 문의해주십쇼 코드: "+imp_uid;
            }

        }
        // 실패
        else {
            log.error("실제 결제된 금액과 주문금액이 다릅니다. Reservation Id: {} ",merchant_uid);
//                reservationInfo.cancel();
            return "실제 결제된 금액과 주문금액이 다릅니다. 자동 환불되지 않으니, 관리자에게 문의하여 주십쇼. 예약번호:"+merchant_uid;
        }


    }

    private ReservationPayment savePackageTourPayment(ReservationPayment reservationPayment) {
        for (int i = 0; i<10;i++){
            boolean conflict = reservationPaymentRepository.existsById(reservationPayment.idGenerate());
            if (!conflict) {
                return reservationPaymentRepository.save(reservationPayment);
            }else {
                if(i == 9){
                    log.info("결제코드 10연속 중복, 오늘자 :{}", LocalDateTime.now());
                    return null;
                }
            }
        }
        return null;
    }
}
