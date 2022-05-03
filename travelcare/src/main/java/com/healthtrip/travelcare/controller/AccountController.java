package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import com.healthtrip.travelcare.repository.dto.response.AccountResponseDto;
import com.healthtrip.travelcare.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    @Operation(summary = "아이디 생성",description = "AccountRequestDto을 참고")
    @PostMapping("")
    public ResponseEntity accounts(@RequestBody AccountRequestDto accountRequestDto){
        return accountService.create(accountRequestDto);

    }

    /*
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x",value = " x 값이오 ",required = true,dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "y",value = " y 값이오 ",required = true,dataType = "int",paramType = "query")
    })
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountRequestDto accountRequestDto){
        return accountService.login(accountRequestDto);
    }

    // auth 메소드를 만들어서 아이디 삭제 시 사용 + 로그인 시 사용

}

