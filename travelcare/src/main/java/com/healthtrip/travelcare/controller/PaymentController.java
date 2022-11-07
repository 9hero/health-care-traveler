package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.response.PaymentResponse;
import com.healthtrip.travelcare.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Tag(name = "결제 API")
public class PaymentController {
    private final String domain = "/payment";
    private final String adminApi = "/admin"+domain;
    private final PaymentService paymentService;

    @Operation(summary = "아임포트 결제 검증 API.",description = "imp_uid: 아임포트 결제id <br/> merchant_uid: 예약주문번호")
    @PostMapping(domain+"/verification")
    public String verification(
            @RequestParam(required = false) String imp_uid
            , @RequestParam(required = false) String merchant_uid
         ) throws IamportResponseException, IOException {
        return paymentService.verify(imp_uid,merchant_uid);
    }

    @Operation(summary = "예약 번호로 결제 내역 조회")
    @GetMapping(domain)
    public PaymentResponse getPayment(@RequestParam String merchantId) {
        return paymentService.getByMerchantId(merchantId);
    }
}
