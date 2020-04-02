package com.kyriexu.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.model.RespBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author KyrieXu
 * @since 2020/3/29 20:45
 **/
@Component
public class VerifyCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取前端发过来的验证码
        String code = request.getParameter("code");
        // 获取生成的验证码
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        // 如果不是登陆请求的话，就直接过
        // 否则校验验证码
        if ("POST".equals(request.getMethod()) && "/dologin".equals(request.getServletPath())) {
            // 俩验证码为空或者不相等的话，就给前端提示验证码不对
            if(code==null||verifyCode==null||!verifyCode.toLowerCase().equals(code.toLowerCase())||"".equals(code)){
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码填写错误")));
                out.flush();
                out.close();
            }else
                // 否则验证码正确，直接过
                filterChain.doFilter(servletRequest,servletResponse);
        }else
            // 不是登陆请求，直接过
            filterChain.doFilter(servletRequest,servletResponse);
    }
}
