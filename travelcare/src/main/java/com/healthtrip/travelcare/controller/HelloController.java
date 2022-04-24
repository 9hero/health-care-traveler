package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.HelloDTO;
import com.healthtrip.travelcare.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/helo")
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/")
    public ResponseEntity<HelloDTO> hello() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin","http://localhost:3000");

        HelloDTO helloDTO = helloService.helloService();
        return ResponseEntity.ok().headers(responseHeaders).body(helloDTO);
    }
}
