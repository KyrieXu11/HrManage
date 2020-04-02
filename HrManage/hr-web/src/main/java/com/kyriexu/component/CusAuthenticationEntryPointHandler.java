package com.kyriexu.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.model.RespBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author KyrieXu
 * @since 2020/3/28 0:18
 * 用来解决匿名用户访问无权限资源时的异常
 **/
@Component
public class CusAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        RespBean respBean = RespBean.error("访问失败，请登陆！");
        if(exception instanceof InsufficientAuthenticationException){
            respBean.setMsg("请求失败，请联系管理员");
        }
        writer.write(new ObjectMapper().writeValueAsString(respBean));
        writer.flush();
        writer.close();
    }
}
