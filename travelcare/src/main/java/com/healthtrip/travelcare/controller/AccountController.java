package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.request.RefreshTokenRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import com.healthtrip.travelcare.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name = "계정 API",description = "모두 허용")
public class AccountController {

    private final AccountService accountService;
    @ApiResponses({
//            @ApiResponse(responseCode = "201",headers = {@Header(name = "Location",description = "/")}),
            @ApiResponse(responseCode = "409",description = "이메일 중복"),
            @ApiResponse(responseCode = "400",description = "입력값 오류")
    })
    @Operation(summary = "일반 아이디 생성")
    @PostMapping("/common")
    public ResponseEntity commonSignUp(@RequestBody AccountRequest.commonSignUp commonSignUp){
        return accountService.createCommon(commonSignUp);
    }
    @ApiResponses({
    @ApiResponse(responseCode = "409",description = "이메일 중복"),
    @ApiResponse(responseCode = "400",description = "입력값 오류")
})
    @Operation(summary = "기관 아이디 생성")
    @PostMapping("/agent")
    public ResponseEntity agentSignUp(@RequestBody AccountRequest.agentSignUp agentSignUp){
        return accountService.createAgent(agentSignUp);
    }

    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "이메일 체크")
    @PostMapping("/email-check")
    public boolean emailCheck(@RequestParam String email) {
        return accountService.emailCheck(email);
    }
    private final AuthenticationManager authenticationManager;

    @ApiResponse(responseCode = "401",description = "로그인 실패")
    @Operation(summary = "로그인",description = "로그인 후 권한이 필요한 API 요청시 Authorization 헤더에 (Bearer 'jwt')")
    @PostMapping("/login")
    public ResponseEntity<AccountResponse> signIn(@RequestBody AccountRequest.SignInDto signInDto){
        return accountService.login(signInDto,authenticationManager);
    }

    @ApiResponse(responseCode = "401",description = "재로그인 필요")
    @Operation(summary = "jwt 토큰 만료시 재발급 요청")
    @PostMapping("/refresh-token")
    public ResponseEntity getToken(@RequestBody RefreshTokenRequest request) {
        return accountService.newAccessToken(request);
    }

    @Operation(summary = "(프론트페이지 필요)비밀번호를 잊었어요 재설정 요청! 흐름: 요청 ->사용자 메일-> 비밀번호 재설정(/reset-password)")
    @PostMapping("/forgot-password")
    public void passwordReset(@RequestBody String email) {
        log.info("비번 초기화 요청의 이메일 :{}",email);
        accountService.sendPasswordResetMail(email);
    }
    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "비밀번호 재설정 Mail -> Form -> This Api",description = "input hidden email,authToken")
    @PostMapping("/reset-password")
    public boolean resetPassword(@ModelAttribute AccountRequest.PasswordReset dto){
        return accountService.passwordReset(dto);
    }

    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "이메일을 통해 회원가입 확인")
    @GetMapping("/confirm") // 프론트 붙으면 패치로로
    public boolean confirmAccount(@RequestParam String email,@RequestParam String authToken){
        return accountService.confirmAccount(email, authToken);
        // 생각 해볼 예외: 만료기간 지남, 잘못된 토큰 or 이메일
    }

    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "이메일 인증 재신청(만료기간이 지났을 경우)")
    @PostMapping
    public boolean reConfirmation(@RequestParam String email) {
        return accountService.reConfirm(email);
    }

    @Operation(hidden = true,summary = "테스트용 권한 확인")
    @GetMapping("/auth")
    public Authentication getAuthTest() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}

