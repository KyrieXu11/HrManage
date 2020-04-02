package com.kyriexu.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author KyrieXu
 * @since 2020/3/28 19:35
 **/
@Component
public class FailLoginHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        StringBuilder sb=new StringBuilder();
        if(exception instanceof UsernameNotFoundException){
            sb.append("用户名未找到");
        }else if(exception instanceof AccountExpiredException){
            sb.append("用户已过期");
        }else if(exception instanceof LockedException){
            sb.append("帐户被锁定");
        }else if(exception instanceof DisabledException){
            sb.append("用户被禁用");
        }else{
            sb.append("未知异常");
        }
        RespBean error = RespBean.error(sb.toString());
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(error));
        out.flush();
        out.close();
    }
}
