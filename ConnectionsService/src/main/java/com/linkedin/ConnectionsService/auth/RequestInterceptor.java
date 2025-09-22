package com.linkedin.ConnectionsService.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String userId = request.getHeader("X-User-Id");
        if(userId !=null){
            AuthContextHolder.setCurrentUserId(Long.parseLong(userId));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        AuthContextHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

   



}
