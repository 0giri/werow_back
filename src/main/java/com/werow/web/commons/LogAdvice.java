package com.werow.web.commons;

import com.werow.web.user.domain.User;
import com.werow.web.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAdvice {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;
    private final HttpSession session;

    @Pointcut("execution(* com.werow.web..*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void beforeInfoLog(JoinPoint joinPoint) {
        String clientIP = httpUtils.getClientIP(request);
        String userRole = "Guest";
        User loginedUser = (User) session.getAttribute("user");
        if (loginedUser != null) {
            userRole = loginedUser.getRole().toString();
        }
        String method = joinPoint.getSignature().toShortString();

        log.info("[IP] {} , [Role] {} , [Request] {}", clientIP, userRole, method);
    }

    @Before("controllerPointcut()")
    public void beforeLog(JoinPoint joinPoint) {
        try {
            String method = joinPoint.getSignature().toString();
            StringBuilder sb = new StringBuilder();
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                for (Object obj : args) {
                    sb.append(obj + ", ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
            }
            log.debug("[BEFORE] {} | [ARGS] : {}", method, sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Around("controllerPointcut()")
    public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().toShortString();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object obj = pjp.proceed();

        stopWatch.stop();

        log.debug("[AROUND] {} | [TIMER] : {}ms", method, stopWatch.getTotalTimeMillis());
        return obj;
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "returnObj")
    public void afterLog(JoinPoint joinPoint, Object returnObj) {
        String shortMethod = joinPoint.getSignature().toShortString();
        log.debug("[AFTER] {} | [RETURN] : {}", shortMethod, returnObj);
    }
}
