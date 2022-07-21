package com.healthtrip.travelcare.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import com.healthtrip.travelcare.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    private AccountService accountService;

    // 지원하는 필터주소값
    public JwtLoginFilter(AuthenticationManager authenticationManager,AccountService accountService) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/account/sign-in");
        setUsernameParameter("email");
        setPasswordParameter("password");
        this.accountService = accountService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //            System.out.println("obtainUsername test: " + obtainUsername(request) + "password: "+ obtainPassword(request));
//            var userLoginForm = objectMapper.readValue(request.getInputStream(),AccountRequest.SignInDto.class);
//            log.info("유저 폼 작동확인 : "+userLoginForm);


        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        obtainUsername(request),obtainPassword(request)
                );
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 인증완료한 객체 찾아오기 -> dto 만들기 ->json 만들기
        Account account = (Account) authResult.getPrincipal();

        AccountResponse accountResponse = accountService.getAccountInfoWithTokens(account);
        response.setHeader(HttpHeaders.AUTHORIZATION,"Bearer " + accountResponse.getJwt());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(accountResponse));
        //
    }

}
