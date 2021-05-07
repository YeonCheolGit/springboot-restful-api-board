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
     * 토큰 생성 한다
     */
    public String createToken(String userPk, List<Role> roles) {
        log.debug("createToken");
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
     * Request Header에서 토큰 값 가지고 온다
     */
    public String resolveToken(HttpServletRequest request) {
        log.debug("resolveToken");
        return request.getHeader("X-AUTH-TOKEN");
    }

    /*
     * 토큰 유효성 + 만료일자 검증
     */
    public boolean validateToken(String jwtToken) {
        log.debug("validateToken");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date()); // 만료 날짜가 현재보다 이전이 아니라면
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /*
     * 토큰이 유효하다면 실행 됨
     * 토큰을 기준으로 DB에 저장된 회원 가지고 옮
     * 토큰 형태로 만들어 인증을 위임
     */
    public Authentication getAuthentication(String token) {
        log.debug("getAuthentication");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
