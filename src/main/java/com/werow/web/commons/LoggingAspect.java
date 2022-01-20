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

    /**
     * 컨트롤러 클래스의 메소드만 포함하는 포인트컷
     */
    @Pointcut("execution(* com.werow.web..*Controller.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 컨트롤러의 메소드 실행 전 클라이언트 접속 IP 로깅
     */
    @Before("controllerPointcut()")
    public void beforeController(JoinPoint jp) {
        String clientIP = httpUtils.getClientIP(request);
        String method = jp.getSignature().toShortString();
        log.info("[IP] {} , [Method] {}", clientIP, method);
    }

    /**
     * 컨트롤러의 메소드 실행 소요시간 로깅
     */
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

}