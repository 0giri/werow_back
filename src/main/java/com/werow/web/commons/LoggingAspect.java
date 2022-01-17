package com.werow.web.commons;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;

    @Pointcut("execution(* com.werow.web..*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void beforeController(JoinPoint jp) {
        String clientIP = httpUtils.getClientIP(request);
        String method = jp.getSignature().toShortString();
        log.info("[IP] {} , [Method] {}", clientIP, method);
    }

    @Around("controllerPointcut()")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().toShortString();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object obj = pjp.proceed();
        stopWatch.stop();
        log.debug("[Method] {} , [TIMER] : {}ms", method, stopWatch.getTotalTimeMillis());
        return obj;
    }

//    @Before("controllerPointcut()")
//    public void beforeController(JoinPoint jp) {
//        String clientIP = httpUtils.getClientIP(request);
//        Role role = Role.GUEST;
//        try {
//            role = jwtUtils.getTokenInfo(request).getRole();
//        } catch (Exception ignored) {
//        }
//        String method = jp.getSignature().toShortString();
//        log.info("[IP] {} , [Role] {} , [Method] {}", clientIP, role, method);
//    }
}