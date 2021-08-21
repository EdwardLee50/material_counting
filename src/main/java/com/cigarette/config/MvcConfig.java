package com.cigarette.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * mvc配置类
 * @author Edward
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 解决跨域请求问题的配置类，可通过设置如下注解起到同样效果
     * " @CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*") "
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedOriginPatterns("*"). //允许跨域的域名，可以用*表示允许任何域名使用
                        allowedMethods("*"). //允许任何方法（post、get等）
                        allowedHeaders("*"). //允许任何请求头
                        allowCredentials(true). //带上cookie信息
                        exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
            }
        };
    }

    /* ======================== 配置 swagger2 ========================*/

    ///**
    // * 访问 http://localhost:8083/swagger-ui.html 可以看到API文档@Bean
    // */
    //@Bean
    //public Docket api(){
    //    return new Docket(DocumentationType.SWAGGER_2)
    //            .apiInfo(apiInfo())
    //            .pathMapping("/")
    //            // 选择那些路径和api会生成document
    //            .select()
    //            // 对所有api进行监控
    //            .apis(RequestHandlerSelectors.any())
    //            //不显示错误的接口地址
    //            .paths(Predicates.not(PathSelectors.regex("/error.*")))
    //            .build();
    //}
    //
    //private ApiInfo apiInfo() {
    //    return new ApiInfoBuilder()
    //            .title("material counting api")
    //            .description("material counting的接口文档")
    //            .version("1.0")
    //            .build();
    //}
    //
    ///**
    // * 配置swagger2的静态资源路径
    // * @param registry
    // */
    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    // 解决静态资源无法访问
    //    registry.addResourceHandler("/**")
    //            .addResourceLocations("classpath:/static/");
    //    // 解决swagger无法访问
    //    registry.addResourceHandler("/swagger-ui.html")
    //            .addResourceLocations("classpath:/META-INF/resources/");
    //    // 解决swagger的js文件无法访问
    //    registry.addResourceHandler("/webjars/**")
    //            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    //}
}

