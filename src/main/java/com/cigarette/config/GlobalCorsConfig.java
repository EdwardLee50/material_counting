package com.cigarette.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * @author EdwardLee
 * @create 2021-08-17 20:37
 */
public class GlobalCorsConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        //config.addAllowedOrigin("http://manage.leyou.com");
        //config.addAllowedOrigin("http://www.leyou.com");
        config.addAllowedOrigin("*");
        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.PATCH);
        config.addAllowedMethod(HttpMethod.TRACE);
        config.addAllowedMethod(HttpMethod.HEAD);
        config.setMaxAge(3600L);
        // 4）允许的头信息
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        //return new CorsFilter(configSource);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        bean.setName("corsFilterConfig");
        bean.setOrder(1);
        return bean;
    }
}
