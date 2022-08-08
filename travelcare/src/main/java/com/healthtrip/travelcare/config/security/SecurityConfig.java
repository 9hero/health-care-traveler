package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.config.security.jwt.JwtCheckFilter;
import com.healthtrip.travelcare.config.security.jwt.JwtLoginFilter;
import com.healthtrip.travelcare.config.security.jwt.JwtProvider;
import com.healthtrip.travelcare.service.AccountService;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v3/api-docs/**",
                "/swagger-ui/**",
                "/favicon.ico"
        );
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_COMMON");
        return roleHierarchy;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        JwtLoginFilter loginFilter = new JwtLoginFilter(authenticationManager(),accountService);
        JwtCheckFilter jwtCheckFilter = new JwtCheckFilter(jwtProvider,accountService);
        XssEscapeServletFilter xssEscapeServletFilter = new XssEscapeServletFilter();

        http.csrf().disable().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/account/**","/favicon.ico").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")

                // address
                .antMatchers("/api/address/**").authenticated()

                //reservation date

                // notice board
                .antMatchers(HttpMethod.GET,"/api/notice-board/**").permitAll()

                // trip pack file
                .antMatchers(HttpMethod.GET,"/api/tour/package/file/images").permitAll()


                // trip package
                .antMatchers(HttpMethod.GET,"/api/trip-package/**").permitAll()

                // reservation
                .antMatchers("/api/tour/reservation/**").authenticated()
                // custom reservation
                .antMatchers("/api/tour/custom/**").authenticated()

                .anyRequest().authenticated()
                .and()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://localhost:3000","*"));
                    cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS","*"));
                    cors.setAllowedHeaders(List.of("*"));
                    return cors;
                }).and()
                .addFilterAt(xssEscapeServletFilter, CsrfFilter.class)
                .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.setStatus(401));
    }

}
