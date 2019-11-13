package com.seven.config;


import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 */

@ControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Object defaultErrorhandle(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        Map<String,Object> map = new HashMap<>();
        map.put("code",201);
        map.put("msg","服务器内部错误");
        map.put("data","");
        return map;
    }


    /**
     * 404页面处理
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return (factory -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND,"/404.do");
            factory.addErrorPages(error404Page);
        });
    }



}

