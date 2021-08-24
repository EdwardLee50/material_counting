package com.cigarette.filter;

import com.cigarette.common.annotation.NoRepeatSubmit;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.response.ApiRestResponse;
import com.cigarette.common.utils.JwtUtils;
import org.apache.commons.collections4.map.LRUMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
 * @create 2021-08-20 14:17
 */
@Aspect
@Component
public class NoRepeatSubmitAop {

    private Logger logger = LoggerFactory.getLogger(NoRepeatSubmitAop.class);

    private static LRUMap<String, Integer> cache = new LRUMap<>(1000);

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public ApiRestResponse arround(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws BusinessException {

        // 生成唯一key，由 userId + url + http method + args 组成
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Integer userId = (Integer) JwtUtils.getInfoByJwtToken(request);
        if (userId == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN);
        }
        String url = request.getRequestURI().toString();
        String method = request.getMethod();
        String args = Arrays.toString(pjp.getArgs());
        String key = userId + url + method + args;

        try {
            synchronized (NoRepeatSubmitAop.class) {
                // 如果缓存中没有这个 key 则进行处理
                if (!cache.containsKey(key)) {
                    // 添加 key
                    cache.put(key, 1);
                    Object o = pjp.proceed();
                    // 删除key
                    cache.remove(key);
                    return ApiRestResponse.success(o);
                } else {
                    logger.error("Request duplicate submission.");
                    return ApiRestResponse.error(EnumBusinessError.REQUEST_DUPLICATE_SUBMISSION);
                }
            }
        } catch (Throwable e) {
            logger.error("An exception occurred while requesting duplicate submissions:" + e);
            // 执行异常的 key 手动释放掉
            cache.remove(key);
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                return ApiRestResponse.error(businessException.getCommonError());
            } else {
                return ApiRestResponse.error(EnumBusinessError.UNKNOWN_ERROR);
            }
        }
    }
}
