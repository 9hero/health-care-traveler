package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import com.healthtrip.travelcare.repository.dto.response.AccountResponseDto;
import com.healthtrip.travelcare.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("")
    public ResponseEntity accounts(@RequestBody AccountRequestDto accountRequestDto){
        return accountService.create(accountRequestDto);

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountRequestDto accountRequestDto){
        return accountService.login(accountRequestDto);
    }

    // auth 메소드를 만들어서 아이디 삭제 시 사용 + 로그인 시 사용

}

