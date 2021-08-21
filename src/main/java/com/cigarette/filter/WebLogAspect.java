package com.cigarette.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author EdwardLee
 * @create 2021-08-09 14:25
 * 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {

    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 配置切入点表达式
     */
    @Pointcut("execution(public * com.cigarette.controller.*.*(..)))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        //收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD :" + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS : " + joinPoint.getSignature().getDeclaringTypeName());
        log.info("METHOD" + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        // 若携带token，记录token
        String mcToken = request.getHeader("MC_TOKEN");
        if(mcToken != null){
            log.info("MC_TOKEN : " + mcToken);
        }
    }

    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求，返回内容
        log.info("RESPONSE : " + new ObjectMapper().writeValueAsString(res));
    }
}
