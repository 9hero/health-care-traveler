package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtProvider.resolveToken(request);
        try{
            // 유효한 토큰인지 확인합니다.
            if (token != null && jwtProvider.validateJwtToken(token)){
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                String username =(String) jwtProvider.getClaims(token).get("email");
                UserDetails userDetails = accountService.loadUserByUsername(username); // grobalhandler 받기 https://github.com/murraco/spring-boot-jwt/blob/master/src/main/java/murraco/security/JwtTokenFilter.java
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,"",userDetails.getAuthorities()
                );
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            response.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        } catch (UsernameNotFoundException e){
            response.setHeader("cause",e.getMessage());
            response.sendError(401,e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
