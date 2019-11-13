package com.seven.Interceptor;

import com.seven.annotation.CheckToken;
import com.seven.annotation.NoLogin;
import com.seven.util.JwtTokenUtil;
import com.seven.util.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 没有加登陆注解的直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有NoLogin注释，有则放行
        if (method.isAnnotationPresent(NoLogin.class)) {
            return true;
        }

        CheckToken beanToken = handlerMethod.getBeanType().getAnnotation(CheckToken.class);
        CheckToken methodToken = handlerMethod.getMethod().getAnnotation(CheckToken.class);
        //方法上的注解优先
        CheckToken checkToken = methodToken == null ? beanToken : methodToken;

        if(checkToken != null){
            String accessToken = request.getHeader("token");
            if (null == accessToken || "".equals(accessToken)) {
                throw new MyException(HttpStatus.MULTIPLE_CHOICES.value(),"token is null!");
            } else {
                if(JwtTokenUtil.isExpiration(accessToken)){
                    throw new MyException(HttpStatus.MOVED_PERMANENTLY.value(),"token is Expiration!");
                }

                //匹配token


                //重新生成token
                String token = JwtTokenUtil.createToken("","",false);
                response.setHeader("token",token);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
