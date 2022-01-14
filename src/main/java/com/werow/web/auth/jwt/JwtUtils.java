package com.werow.web.auth.jwt;

import com.werow.web.account.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

/**
 * 토큰 관련 로직
 */
@Component
@Slf4j
public class JwtUtils implements InitializingBean {

    private final String SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRATION_MS;
    private final long REFRESH_TOKEN_EXPIRATION_MS;
    private Key key;

    /**
     * yml에 설정한 값들을 가져와 변수 초기화
     */
    public JwtUtils(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-expiration-min}") long accessTokenExpirationMin,
            @Value("${jwt.refresh-expiration-min}") long refreshTokenExpirationMin) {
        this.SECRET_KEY = secretKey;
        this.ACCESS_TOKEN_EXPIRATION_MS = accessTokenExpirationMin * 60 * 1000;
        this.REFRESH_TOKEN_EXPIRATION_MS = refreshTokenExpirationMin * 60 * 1000;
    }

    /**
     * 빈 생성된 직후 secret-key를 디코딩하여 Key 인스턴스 생성
     */
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_EXPIRATION_MS);
    }

    public String createRefreshToken(User user) {
        return createToken(user, REFRESH_TOKEN_EXPIRATION_MS);
    }

    private String createToken(User user, Long expirationMs) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("0giri")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .setSubject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(HttpServletRequest request) {
        String token = resolveToken(request);
        return isValidatedToken(token);
    }

    /**
     * request의 Authorization 헤더에서 토큰만 추출
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 토큰 유효성 검증
     */
    private boolean isValidatedToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
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
