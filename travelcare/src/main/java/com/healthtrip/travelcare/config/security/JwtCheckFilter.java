package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.service.AccountService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        System.out.println("여기인가 g ?");
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtProvider.validateJwtToken(request, token)) {
            System.out.println("여기 지나감?");
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            String username = jwtProvider.getUserInfo(token);
            try{

            UserDetails userDetails = accountService.loadUserByUsername(username); // grobalhandler 받기 https://github.com/murraco/spring-boot-jwt/blob/master/src/main/java/murraco/security/JwtTokenFilter.java
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,"",userDetails.getAuthorities()
            );
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                e.printStackTrace();
                filterChain.doFilter(request,response);
            }
        }
        filterChain.doFilter(request, response);
    }
}
