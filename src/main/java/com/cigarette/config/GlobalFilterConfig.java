//package com.cigarette.config;
//
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
//public class GlobalFilterConfig {
//
//    @Bean
//    public GlobalLoginFilter globalFilter(){
//        return new GlobalLoginFilter();
//    }
//
//    @Bean(name = "globalFilterConf")
//    public FilterRegistrationBean globalFilterConfig(){
//        FilterRegistrationBean<GlobalLoginFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//        filterFilterRegistrationBean.setFilter(globalFilter());
//        // 过滤所有除login方法
//        filterFilterRegistrationBean.addUrlPatterns("/user/*");
//        filterFilterRegistrationBean.addUrlPatterns("/order/*");
//        filterFilterRegistrationBean.addUrlPatterns("/shop/*");
//        filterFilterRegistrationBean.addUrlPatterns("/batch/*");
//        //filterFilterRegistrationBean.addUrlPatterns("/file/*");
//        filterFilterRegistrationBean.setName("globalFilterConf");
//        filterFilterRegistrationBean.setOrder(2);
//        return filterFilterRegistrationBean;
//    }
//}
