package com.emosoft.cloud.filesharing.web.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;


@Component
public class LoggingFilters implements HandlerInterceptor {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.print(ANSI_RED);
        System.out.println("***** Req URL: "+ LocalDateTime.now()+" --- " + request.getRequestURL()+" *****");
        System.out.println("***** Req Remote Host: "+ LocalDateTime.now()+" --- " +request.getRemoteHost()+" *****");
        System.out.println(ANSI_RESET);
    }
}
