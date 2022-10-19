package com.healthtrip.travelcare.controller;

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
@Tag(name = "아임 포트 결제 API")
public class PaymentController {
    private final String domain = "/payment";
    private final String adminApi = "/admin"+domain;
    private final PaymentService paymentService;

    @Operation(summary = "PG사를 통해 결제가 완료되고 받아온 uid로 검증한다.",description = "결과값은 String으로 알려준다.")
    @PostMapping(domain+"/verification")
    public String verification(
            @RequestParam(required = false) String imp_uid
            , @RequestParam(required = false) String merchant_uid
         ) throws IamportResponseException, IOException {
        return paymentService.verify(imp_uid,merchant_uid);
    }
}
