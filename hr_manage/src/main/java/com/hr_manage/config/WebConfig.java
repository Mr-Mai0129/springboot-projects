package com.hr_manage.config;

import com.hr_manage.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 配置跨域请求
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //配置跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new JwtInterceptor());
        //拦截的路径
        interceptorRegistration.addPathPatterns("/**/add.do","/**/delete.do","/**/update.do");
        //放行的路径
//        interceptorRegistration.excludePathPatterns("/**/user/update.do");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/**")
                .addResourceLocations("file:D:/idea/IdeaProjects/hr_manage/src/main/resources/files/");
    }
}
