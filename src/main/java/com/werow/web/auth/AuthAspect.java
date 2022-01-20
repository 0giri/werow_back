package com.werow.web.auth;

import com.werow.web.account.entity.enums.Role;
import com.werow.web.auth.annotation.*;
import com.werow.web.auth.dto.TokenInfo;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.NotCorrectTokenType;
import com.werow.web.exception.NotEnoughAuthorityException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Aspect
@Component
public class AuthAspect {

    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;

    @Before("@annotation(roleUser)")
    public void checkIsUser(RoleUser roleUser) {
        checkRole(Role.USER);
    }

    @Before("@annotation(roleFreelancer)")
    public void checkIsFreelancer(RoleFreelancer roleFreelancer) {
        checkRole(Role.FREELANCER);
    }

    @Before("@annotation(roleManager)")
    public void checkIsManager(RoleManager roleManager) {
        checkRole(Role.MANAGER);
    }

    @Before("@annotation(roleAdmin)")
    public void checkIsAdmin(RoleAdmin roleAdmin) {
        checkRole(Role.ADMIN);
    }

    /**
     * 토큰 검증
     */
    private void checkRole(Role role) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        String tokenType = tokenInfo.getTokenType();
        if (!tokenType.equals(JwtUtils.ACCESS)) {
            throw new NotCorrectTokenType("Access 토큰이 아닙니다");
        }
        Role tokenRole = tokenInfo.getRole(); // 매번 토큰의 ID값으로 유저 다시 조회해 롤 구할지 고민

        if (tokenRole.getValue() < role.getValue()) {
            throw new NotEnoughAuthorityException(role.name() + " 이상의 권한이 필요합니다.");
        }
    }
}
