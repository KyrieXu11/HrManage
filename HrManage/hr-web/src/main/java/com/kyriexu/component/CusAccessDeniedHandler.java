package com.kyriexu.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.model.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author KyrieXu
 * @since 2020/3/28 23:40
 * 用来解决已认证用户访问无效权限的异常处理
 **/
@Component
public class CusAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        String message = e.getMessage();
        PrintWriter out = response.getWriter();
        RespBean error = RespBean.error(message);
        String s = new ObjectMapper().writeValueAsString(error);
        out.write(s);
        out.flush();
        out.close();
    }
}
