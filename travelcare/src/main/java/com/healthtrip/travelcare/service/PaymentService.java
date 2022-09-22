package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.PackageTourPayment;
import com.healthtrip.travelcare.repository.tour.PackageTourPaymentRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final IamportClient iamportClient;
    private final TourReservationRepository tourReservationRepository;
    private final PackageTourPaymentRepository packageTourPaymentRepository;
    public String verify(String imp_uid, String merchant_uid) throws IamportResponseException, IOException {
        // 결제내역 조회
        IamportResponse<Payment> result = iamportClient.paymentByImpUid(imp_uid);
        Payment payment = result.getResponse();

        // 주문내역 조회
        var infoOptional = tourReservationRepository.findById(merchant_uid);
        if (infoOptional.isPresent()) {
            // 실제 결제금액과 예약 상품 금액 비교
            var reservationInfo = infoOptional.get();
            // 성공
            if (result.getResponse().getAmount().compareTo(reservationInfo.getAmount()) == 0) {
                // 결제내역 DB 저장
                PackageTourPayment entity = PackageTourPayment.builder()
                        .payType(payment.getPayMethod())
                        .currency(payment.getCurrency())
                        .paymentDate(LocalDateTime.ofInstant(payment.getPaidAt().toInstant(), ZoneId.systemDefault()))
                        .tourReservation(reservationInfo)
                        .amount(payment.getAmount())
                        .build();
                boolean saved = savePackageTourPayment(entity) != null;

                // 예약 결제완료
//                reservationInfo.paid();
                if (saved){
                    return "결제완료";
                }else {
                    return "결제완료, 결제정보등록실패 코드와 함께 관리자에게 반드시 문의해주십쇼 코드: "+imp_uid;
                }

            }
            // 실패
            else {
//                reservationInfo.cancel();
                return "실제 결제된 금액과 주문금액이 다릅니다. 자동 환불되지 않으니, 관리자에게 문의하여 주십쇼.";
            }
        } else { //해당 주문내역이 없음
            return "검증실패";
        }

    }

    private PackageTourPayment savePackageTourPayment(PackageTourPayment packageTourPayment) {
        for (int i = 0; i<10;i++){
            boolean conflict = packageTourPaymentRepository.existsById(packageTourPayment.idGenerate());
            if (!conflict) {
                return packageTourPaymentRepository.save(packageTourPayment);
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
