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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name = "계정 API")
public class AccountController {

    private final AccountService accountService;

    @ApiResponses({
            @ApiResponse(responseCode = "201",headers = {@Header(name = "Location",description = "/")}),
            @ApiResponse(responseCode = "409",description = "이메일 중복"),
    })
    @Operation(summary = "일반 아이디 생성")
    @PostMapping("/common")
    public ResponseEntity commonSignUp(@RequestBody AccountRequest.commonSignUp commonSignUp){
        return accountService.createCommon(commonSignUp);
    }
    @Operation(summary = "기관 아이디 생성")
    @PostMapping("/agent")
    public ResponseEntity agentSignUp(@RequestBody AccountRequest.agentSignUp agentSignUp){
        return accountService.createAgent(agentSignUp);
    }

    // 이메일체크
    @Operation(summary = "이메일 체크")
    @PostMapping("/email-check")
    public boolean emailCheck(@RequestParam String email) {
        return accountService.emailCheck(email);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<AccountResponse> signIn(@RequestBody AccountRequest.SignInDto signInDto){
        return accountService.login(signInDto);
    }

//    @Operation(summary = "메일 테스트")
//    @PostMapping("/mailtest")
//    public void mail(@RequestBody MailRequest mailRequest) {
//        accountService.mailTest(mailRequest);
//    }
}

