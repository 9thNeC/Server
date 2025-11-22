package com.example.Server.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;    // application.yml에서 불러옴

    @Value("${jwt.expiration}")  // 토큰 만료시간(ms)
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        // HS256 Key 생성
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWT 토큰 생성
     */
    public String createToken(String socialId, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration * 60 * 1000L);

        return Jwts.builder()
                .setSubject(socialId)  // socialId를 주체로 변경
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 이메일(subject) 가져오기
     */
    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰에서 socialId 가져오기
     */
    public String getSocialId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // sub 값!
    }



    /**
     * 토큰에서 role 가져오기
     */
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT Token 만료됨");
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT Token 유효하지 않음");
        }
        return false;
    }
    /**
     * Claims 파싱
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }

        return null;
    }

}
