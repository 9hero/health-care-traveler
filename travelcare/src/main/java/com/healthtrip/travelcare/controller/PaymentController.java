package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RequestMapping("/api/payment")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/verification")
    public String verification(
            @RequestParam(required = false) String imp_uid
            , @RequestParam(required = false) String merchant_uid
         ) throws IamportResponseException, IOException {


        return paymentService.verify(imp_uid,merchant_uid);
    }
}
