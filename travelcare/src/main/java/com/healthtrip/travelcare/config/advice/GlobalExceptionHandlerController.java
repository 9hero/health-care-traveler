package com.healthtrip.travelcare.config.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthtrip.travelcare.common.Exception.CustomException;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {

  private final ObjectMapper objectMapper;

  @Bean
  public ErrorAttributes errorAttributes() {
    // Hide exception field in the return object
    return new DefaultErrorAttributes() {
      @Override
      public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().excluding(ErrorAttributeOptions.Include.EXCEPTION));
      }
    };
  }

  @ExceptionHandler(CustomException.class)
  public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
    res.sendError(ex.getHttpStatus().value(), ex.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
  }

//  @ExceptionHandler(Exception.class)
//  public void handleException(HttpServletResponse res,Exception e) throws IOException {
//    res.setStatus(500);
//    res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//    res.getOutputStream().write(objectMapper.writeValueAsBytes(Map.of("error msg",e.getMessage())));
////    res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//  }

  @ExceptionHandler(IamportResponseException.class)
  public void handleIamportException(HttpServletResponse res,IamportResponseException e) {
    log.error("iamport error : {} cause", e.getMessage(), e.getCause());

    switch (e.getHttpStatusCode()) {
      case 401:
        //TODO : 401 Unauthorized
        break;
      case 404:
        //TODO : imp_123412341234 에 해당되는 거래내역이 존재하지 않음
        break;
      case 500:
        //TODO : 서버 응답 오류
        break;
    }
  }
}
