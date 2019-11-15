package com.seven.Interceptor;


import com.seven.request.domain.vo.UserVo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class FileIntrtceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("在请求之前调用，根据配置只拦截HTML，打印拦截地址：----"+request.getServletPath());
        Object loginName = request.getSession().getAttribute("UserName");
        List<UserVo> userUrl = (List<UserVo>) request.getSession().getAttribute("userUrl");
        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(1,servletPath.length());
        if(loginName == null || userUrl == null){
            response.sendRedirect("login.html");
            return false;
        }else if(loginName != null && userUrl.size()<1 && "index.html".equals(servletPath)){
            response.sendRedirect("404.html");
            return false;
        }else if(loginName != null && userUrl.size() > 0 && "index1.html".equals(servletPath)){
            response.sendRedirect("404.html");
            return false;
        }
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println(">>>FileIntrtceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println(">>>FileIntrtceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
