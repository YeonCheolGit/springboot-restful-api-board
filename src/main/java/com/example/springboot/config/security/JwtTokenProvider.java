package com.example.springboot.config.security;

import com.example.springboot.entity.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Log4j2
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final long tokenValidMillis = 100L * 60 * 60;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /*
     Params:
        userPk - 이미 인증된 회원의 PK
        roles - 이미 인증된 회원의 role
     Return: 생성된 String 값의 JWT 토큰
     */
    public String createToken(String userPk, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("yeoncheol")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /*
     Param: Request Header에서 파싱한 토큰 값
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    /*
     * 토큰 유효성 + 만료일자 검증
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date()); // 만료 날짜가 현재보다 이전이 아니라면
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /*
     Param: 이미 유효한 토큰
     Return: 인증 주체와 권한을 저장
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token)); // 토큰을 바탕으로 DB에서 회원 조회 합니다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); // 조회한 인증 주체(회원)와 권한
    }

    private String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
