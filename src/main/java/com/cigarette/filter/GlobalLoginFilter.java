package com.cigarette.filter;

import com.alibaba.fastjson.JSON;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author EdwardLee
 * @create 2021-08-10 10:28
 *
 * 全局拦截器，仅放行登录请求
 */
public class GlobalLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String mcToken = request.getHeader("MC_TOKEN");
        boolean checkToken = JwtUtils.checkToken(mcToken);
        if(checkToken){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            userNotLogin(servletResponse,new BusinessException(EnumBusinessError.TOKEN_ILLEGAL));
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void userNotLogin(ServletResponse response, BusinessException exception) {
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
        PrintWriter out = null;
        try {
            out = responseWrapper.getWriter();
            int code = exception.getCode();
            String message = exception.getMessage();
            out.write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
