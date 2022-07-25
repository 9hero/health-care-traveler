package com.healthtrip.travelcare.service;

import com.siot.IamportRestClient.IamportClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    IamportClient iamportClient;

    @Test
    void verify() {
       assertNotNull(iamportClient);
    }
}