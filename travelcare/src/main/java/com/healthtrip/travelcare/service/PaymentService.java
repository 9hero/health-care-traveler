package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.ReservationInfoRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final IamportClient iamportClient;
    private final ReservationInfoRepository reservationInfoRepository;
    public String verify(String imp_uid, String merchant_uid) throws IamportResponseException, IOException {
        IamportResponse<Payment> result = iamportClient.paymentByImpUid(imp_uid);

        // 결제 가격과 검증결과를 비교한다.
        if(result.getResponse().getAmount().compareTo(BigDecimal.valueOf(100)) == 0) {
            System.out.println("검증통과");
        }
    return null;
    }
}
