package com.werow.web.auth.jwt;

import com.werow.web.account.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * 토큰 관련 로직
 */
@Component
@Slf4j
public class JwtUtils implements InitializingBean {

    private final String secretKey;
    private final long expirationMs;
    private Key key;

    /**
     * yml에 설정한 값들을 가져와 변수 초기화
     */
    public JwtUtils(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.token-expiration-sec}") long tokenExpirationSec) {
        this.secretKey = secretKey;
        this.expirationMs = tokenExpirationSec * 1000;
    }

    /**
     * 빈 생성된 직후 secret-key를 디코딩하여 Key 인스턴스 생성
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 회원 정보로 토큰 생성
     */
    public String createToken(User user) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("0giri")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .setSubject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .claim("join_date", user.getCreatedDate())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException exception) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
