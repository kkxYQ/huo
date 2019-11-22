//package com.seven.Interceptor;
//
//import com.seven.Interceptor.FileIntrtceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class SpringBootConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new FileIntrtceptor()).addPathPatterns("/*.html").excludePathPatterns("/login.html").excludePathPatterns("/404.html");
//    }
//}
