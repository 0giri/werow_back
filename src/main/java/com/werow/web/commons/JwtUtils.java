package com.werow.web.commons;

import com.werow.web.account.entity.User;
import com.werow.web.account.entity.enums.Role;
import com.werow.web.auth.dto.TokenInfo;
import com.werow.web.exception.NotBearerTypeException;
import com.werow.web.exception.NotHaveJwtException;
import com.werow.web.exception.NotValidatedJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

    public final static String ACCESS = "Access";
    public final static String REFRESH = "Refresh";

    /**
     * yml에 설정한 값들을 가져와 변수 초기화
     */
    public JwtUtils(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-expiration-sec}") long accessTokenExpirationSec,
            @Value("${jwt.refresh-expiration-sec}") long refreshTokenExpirationSec) {
        this.SECRET_KEY = secretKey;
        this.ACCESS_TOKEN_EXPIRATION_MS = accessTokenExpirationSec * 1000;
        this.REFRESH_TOKEN_EXPIRATION_MS = refreshTokenExpirationSec * 1000;
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
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("0giri")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MS))
                .setSubject(user.getId().toString())
                .claim("tokenType", ACCESS)
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("0giri")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS))
                .setSubject(user.getId().toString())
                .claim("tokenType", REFRESH)
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public TokenInfo getTokenInfo(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        return parseToken(accessToken);
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken)) {
            throw new NotHaveJwtException("JWT 토큰이 없습니다.");
        }
        if (!bearerToken.startsWith("Bearer ")) {
            throw new NotBearerTypeException("토큰이 Bearer 타입이 아닙니다.");
        }
        return bearerToken.substring(7);
    }

    private TokenInfo parseToken(String accessToken) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            String tokenType = body.get("tokenType", String.class);
            Long id = Long.parseLong(body.getSubject());
            String email = body.get("email", String.class);
            Role role = Role.valueOf(body.get("role", String.class));

            return new TokenInfo(tokenType, id, email, role);
        } catch (MalformedJwtException e) {
            throw new NotValidatedJwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new NotValidatedJwtException("만료된 JWT 토큰입니다.");
        }
    }
}
