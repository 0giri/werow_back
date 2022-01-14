package com.werow.web.auth;

import com.werow.web.auth.jwt.JwtUtils;
import com.werow.web.exception.NotValidatedToken;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;

    @Before("@annotation(tokenRequired)")
    public void checkValidateToken(TokenRequired tokenRequired) throws Exception {
        boolean isValidated = jwtUtils.validateToken(request);
        if (!isValidated) {
            throw new NotValidatedToken("유효한 토큰이 아닙니다.");
        }
    }
}
