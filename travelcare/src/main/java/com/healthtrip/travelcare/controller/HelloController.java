package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.HelloDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/helo")
public class HelloController {

    @GetMapping("/")
    public HttpEntity<HelloDTO> hello() {
        return ResponseEntity.ok(HelloDTO.builder().name("wow").name2("nice").build());
    }
}
