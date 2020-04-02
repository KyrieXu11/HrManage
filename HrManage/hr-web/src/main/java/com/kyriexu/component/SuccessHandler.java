package com.kyriexu.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.model.Hr;
import com.kyriexu.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author KyrieXu
 * @since 2020/3/28 19:35
 **/
public class SuccessHandler {
    @Component
    public static class LoginSuccessHandler implements AuthenticationSuccessHandler{

        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
            onAuthenticationSuccess(request, response, authentication);
            chain.doFilter(request, response);
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            Hr user = (Hr) authentication.getPrincipal();
            user.setPassword(null);
            RespBean respBean = RespBean.ok("登陆成功", user);
            out.write(objectMapper.writeValueAsString(respBean));
            out.flush();
            out.close();
        }
    }

    @Component
    public static class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            RespBean respBean = RespBean.ok("注销成功");
            out.write(objectMapper.writeValueAsString(respBean));
            out.flush();
            out.close();
        }
    }
}
