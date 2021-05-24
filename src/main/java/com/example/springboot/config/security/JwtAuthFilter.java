package com.example.springboot.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request); // 현재 요청한 유저의 토큰을 resolve 합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) { // if 토큰 != null && 토큰 validation == true
            Authentication auth = jwtTokenProvider.getAuthentication(token); // 유효한 토큰을 기준으로 인증 작업 합니다.
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder 요청 유저 인증 저장 합니다.
        }
        filterChain.doFilter(request, response);
    }
}
