package com.cigarette.filter;

import com.alibaba.fastjson.JSON;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.utils.JwtUtils;
import com.cigarette.controller.viewObject.UserReturnVo;
import com.cigarette.service.UserService;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author EdwardLee
 * @create 2021-08-10 10:28
 * <p>
 * 放行emp用户的请求
 */
public class EmpLoginFilter implements Filter {

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String mcToken = request.getHeader("MC_TOKEN");
        if (mcToken == null || !JwtUtils.checkToken(mcToken)) {
            userNotLogin(servletResponse, new BusinessException(EnumBusinessError.TOKEN_ILLEGAL));
        } else {
            try {
                UserReturnVo info = userService.getInfo(mcToken);
                //-1,失效用户;1,employee;2,shop_seller;3,admin
                if (info != null && (info.getRole() == 1 || info.getRole() == 3)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    userNotLogin(servletResponse, new BusinessException(EnumBusinessError.USER_PERMISSION_DENIED));
                }
            } catch (BusinessException e) {
                userNotLogin(servletResponse, e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void userNotLogin(ServletResponse response, BusinessException exception) {
        response.setCharacterEncoding(CharEncoding.UTF_8);
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
        PrintWriter out = null;
        try {
            out = responseWrapper.getWriter();
            String s = "{\"code\":" + exception.getCode() + "," +
                    "\"message\":" + exception.getMessage() + "," +
                    "\"data\": null}";
            out.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }
}
