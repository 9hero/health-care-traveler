package com.healthtrip.travelcare.config.beans;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Iamport {

    @Value("iamport.apiKey")
    private String key;
    @Value("iamport.apiSecret")
    private String secret;
    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(key, secret);
    }
}
