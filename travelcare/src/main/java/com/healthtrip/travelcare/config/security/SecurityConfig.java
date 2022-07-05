package com.healthtrip.travelcare.config.security;

import com.healthtrip.travelcare.service.AccountService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.password.PasswordEncoder;
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
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
        JwtLoginFilter loginFilter = new JwtLoginFilter(authenticationManager(),accountService);
        JwtCheckFilter jwtCheckFilter = new JwtCheckFilter(jwtProvider,accountService);
        http.csrf().disable().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/api/account/**","/favicon.ico").permitAll()
                .antMatchers("/api/address/**").authenticated()

                //reservation date
                .antMatchers("/api/reservation-date").hasRole("ADMIN")

                // notice board
                .antMatchers(HttpMethod.GET,"/api/notice-board").permitAll()
                .antMatchers(HttpMethod.POST,"/api/notice-board").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/notice-board").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/notice-board").hasRole("ADMIN")


                // trip pack file
                .antMatchers("/api/trip-package-file/images").hasRole("ADMIN")

                // trip package
                .antMatchers(HttpMethod.POST,"/api/trip-package/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/trip-package/**").permitAll()

                // reservation
                .antMatchers("/api/reservation/**").authenticated()
                // custom reservation
                .antMatchers("/api/custom/**").authenticated()
                .antMatchers(HttpMethod.PATCH,"/api/custom").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/custom/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .cors().disable()
                .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.setStatus(401));
    }

}
