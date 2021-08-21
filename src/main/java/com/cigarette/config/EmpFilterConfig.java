//package com.cigarette.config;
//
//import com.cigarette.filter.EmpLoginFilter;
//import com.cigarette.filter.GlobalLoginFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author EdwardLee
// * @create 2021-08-13 21:17
// *
// * 全局过滤器的配置
// */
//@Configuration
//public class EmpFilterConfig {
//
//    @Bean
//    public EmpLoginFilter empFilter(){
//        return new EmpLoginFilter();
//    }
//
//    @Bean(name = "empFilterConf")
//    public FilterRegistrationBean empFilterConfig(){
//        FilterRegistrationBean<EmpLoginFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//        filterFilterRegistrationBean.setFilter(empFilter());
//        // 对除shop外的add方法进行过滤
//        filterFilterRegistrationBean.addUrlPatterns("/user/add/*");
//        filterFilterRegistrationBean.addUrlPatterns("/order/add/*");
//        filterFilterRegistrationBean.addUrlPatterns("/batch/add/*");
//
//        filterFilterRegistrationBean.addUrlPatterns("/material/*");
//        filterFilterRegistrationBean.addUrlPatterns("/user/sellers/*");
//
//        filterFilterRegistrationBean.setName("empFilterConf");
//        filterFilterRegistrationBean.setOrder(3);
//
//        return filterFilterRegistrationBean;
//    }
//}
