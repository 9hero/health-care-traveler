package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.request.PersonalityRequest;
import com.healthtrip.travelcare.repository.dto.request.RefreshTokenRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import com.healthtrip.travelcare.repository.dto.response.PersonalityResponse;
import com.healthtrip.travelcare.repository.dto.response.TendencyResponse;
import com.healthtrip.travelcare.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
//@SecurityRequirement(name = "no")
@Tag(name = "계정 API",description = "모두 허용")
public class AccountController {


    private final AccountService accountService;
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "생성 완료"),
            @ApiResponse(responseCode = "409",description = "이메일 중복"),
            @ApiResponse(responseCode = "400",description = "입력값 오류")
    })
    @Operation(summary = "일반 아이디 생성")
    @PostMapping("/common")
    public ResponseEntity commonSignUp(@RequestBody AccountRequest.CommonSignUp commonSignUp){
        return accountService.createCommon(commonSignUp);
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "가입 완료"),
    @ApiResponse(responseCode = "409",description = "이메일 중복"),
    @ApiResponse(responseCode = "400",description = "입력값 오류")
})
    @Operation(summary = "기관 아이디 생성")
    @PostMapping("/agent")
    public ResponseEntity agentSignUp(@RequestBody AccountRequest.AgentSignUp agentSignUp){
        return accountService.createAgent(agentSignUp);
    }


    private final AuthenticationManager authenticationManager;

    @ApiResponses({@ApiResponse(responseCode = "200",description = "성공"),
            @ApiResponse(responseCode = "401",description = "로그인 실패",content = @Content(examples= @ExampleObject) )
    })
    @Operation(summary = "로그인",description = "로그인 후 권한이 필요한 API 요청시 Authorization 헤더에 (Bearer 'jwt')")
    @PostMapping("/login")
    public ResponseEntity<AccountResponse> signIn(@RequestBody AccountRequest.SignInDto signInDto){
        return accountService.login(signInDto,authenticationManager);
    }
    @ApiResponses({@ApiResponse(responseCode = "200",description = "성공"),
            @ApiResponse(responseCode = "401",description = "재로그인 필요",content = @Content(examples= @ExampleObject) )
    })
    @Operation(summary = "jwt 토큰 만료시 재발급 요청")
    @PostMapping("/refresh-token")
    public ResponseEntity getToken(@RequestBody RefreshTokenRequest request) {
        return accountService.newAccessToken(request);
    }


    // 이메일 중복 체크 및 인증코드 이메일 전송
    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "이메일 체크 & 이메일 인증코드 전송")
    @PostMapping("/email-check")
    public AccountResponse.EmailCheck emailCheck(@RequestParam String email) {
        return accountService.emailCheck(email);
    }
    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "이메일 인증코드 확인")
    @GetMapping("/confirm") // 프론트 붙으면 패치로로
    public boolean confirmAccount(@RequestParam Long id,@RequestParam String authToken){
        return accountService.confirmAccount(id, authToken);
        // 생각 해볼 예외: 만료기간 지남, 잘못된 토큰 or 이메일
    }

    @Operation(summary = "비밀번호를 잊었어요 재설정 요청!",description = "흐름: 요청 ->사용자 메일-> 인증코드입력 -> 비밀번호 재설정(/reset-password)")
    @PostMapping("/forgot-password")
    public AccountResponse.EmailCheck passwordReset(@RequestParam String email) {
        log.info("비번 초기화 요청의 이메일 :{}",email);
        return accountService.sendPasswordResetMail(email);
    }
    @ApiResponse(responseCode = "200",description = "성공 실패 = True False")
    @Operation(summary = "비밀번호 재설정 Mail -> Form -> This Api")
    @PostMapping("/reset-password")
    public boolean resetPassword(AccountRequest.PasswordReset dto){
        return accountService.passwordReset(dto);
    }


    // 심리
    @Operation(summary = "내 심리유형 보기")
    @GetMapping("/personality")
    public PersonalityResponse getMyPersonalty() {
        return accountService.getMyPersonality();
    }

    @Operation(summary = "심리유형 결과 저장 후 받기(save & get)")
    @PostMapping("/personality")
    public TendencyResponse setPersonality(@RequestBody PersonalityRequest request) {
        return accountService.setPersonality(request);
    }

}

