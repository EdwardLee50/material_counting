package com.cigarette.controller;

import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.response.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EdwardLee
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiRestResponse handleException(HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse,
                                           Exception ex) {
        String errorMessage = EnumBusinessError.UNKNOWN_ERROR.getMessage();
        if (ex instanceof ServletRequestBindingException) {
            errorMessage = "url绑定路由问题";
        } else if (ex instanceof NoHandlerFoundException) {
            errorMessage = "未找到对应路径，请检查url是否正确";
        }
        logger.error("Default Exception:", ex);
        return ApiRestResponse.error(EnumBusinessError.UNKNOWN_ERROR.getCode(), errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiRestResponse handleBusinessException(HttpServletRequest httpServletRequest,
                                                   HttpServletResponse httpServletResponse,
                                                   BusinessException ex) {
        logger.error("Business Exception:", ex);
        return ApiRestResponse.create(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(HttpServletRequest httpServletRequest,
                                                                 HttpServletResponse httpServletResponse,
                                                                 MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValid Exception:", ex);
        ApiRestResponse result = handleBindResult(ex.getBindingResult());
        return ApiRestResponse.error(result.getCode(), result.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(HttpServletRequest httpServletRequest,
                                                                 HttpServletResponse httpServletResponse,
                                                                 IOException ex) {
        logger.error("IOException Exception:", ex);
        return ApiRestResponse.error(EnumBusinessError.FILE_NOT_EXISTS);
    }

    /**
     * 用于解析并包装前端传参异常并返回
     * @param result
     * @return
     */
    private ApiRestResponse handleBindResult(BindingResult result) {
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String defaultMessage = objectError.getDefaultMessage();
                list.add(defaultMessage);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.create(EnumBusinessError.PARAMETER_VALIDATION_ERROR.getCode(),
                    EnumBusinessError.PARAMETER_VALIDATION_ERROR.getMessage());
        }
        return ApiRestResponse.error(EnumBusinessError.PARAMETER_VALIDATION_ERROR.getCode(), list.toString());
    }

}
