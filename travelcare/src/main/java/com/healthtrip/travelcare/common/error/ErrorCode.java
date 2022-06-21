package com.healthtrip.travelcare.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UsernameOrPasswordNotFoundException ("아디 비번 틀림", HttpStatus.UNAUTHORIZED),
    ForbiddenException("해당 요청에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZEDException ("로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ExpiredJwtException("기존 토큰이 만료되었습니다. 해당 토큰을 가지고 get-newtoken링크로 이동해주세요.", HttpStatus.UNAUTHORIZED),
    ReLogin("모든 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    ;


    private String msg;
    private HttpStatus status;

    ErrorCode(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
