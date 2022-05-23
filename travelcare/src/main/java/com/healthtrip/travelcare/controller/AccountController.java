package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import com.healthtrip.travelcare.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name = "계정 API")
public class AccountController {

    private final AccountService accountService;

    @ApiResponses({
            @ApiResponse(responseCode = "201",headers = {@Header(name = "Location",description = "/")}),
            @ApiResponse(responseCode = "401",description = "이메일 중복"),
    })
    @Operation(summary = "아이디 생성")
    @PostMapping("")
    public ResponseEntity signUp(@RequestBody AccountRequest.SignUpDto signUpDto){
        return accountService.create(signUpDto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<AccountResponse> signIn(@RequestBody AccountRequest.SignInDto signInDto){
        return accountService.login(signInDto);
    }


}

