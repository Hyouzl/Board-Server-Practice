package com.traffic.practice.aop;

import com.traffic.practice.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Log4j2
public class LoginCheckAspect {

    @Around("@annotation(com.traffic.practice.aop.LoginCheck) && @annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {

        // 세션 정보 가져오기
        HttpSession session = (HttpSession) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        String id = null;
        int idIndex = 0;

        String userType = loginCheck.type().toString();
        switch (userType) {
            case "ADMIN" :
                id = SessionUtil.getLoginAdminId(session);
                break;
            case "USER" :
                id = SessionUtil.getLoginMemberId(session);
                break;
        }

        if (id == null) {

            log.error(joinPoint.toString());
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인 한 ID를 확인해주세요") {};
        }

        Object[] modifiedArgs = joinPoint.getArgs();

        if(joinPoint.getArgs() != null)
            modifiedArgs[idIndex] = id;

        return joinPoint.proceed(modifiedArgs);

    }

}
