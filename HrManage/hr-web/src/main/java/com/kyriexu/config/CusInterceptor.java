//package com.kyriexu.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import java.io.IOException;
//
///**
// * @author KyrieXu
// * @since 2020/3/28 13:39
// **/
//@Component
//@Slf4j
//public class CusInterceptor extends AbstractSecurityInterceptor implements Filter {
//
//    @Autowired
//    private CusMetaDataSource metaDataSource;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        log.info("过滤器被初始化");
//    }
//
//    /**
//     * 参考 {@see FilterSecurityInterceptor.class} 这个类
//     *
//     * @param servletRequest  request对象
//     * @param servletResponse response对象
//     * @param filterChain     过滤器链对象
//     * @throws IOException      io异常
//     * @throws ServletException servlet异常
//     */
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
//        invoke(fi);
//    }
//
//    private void invoke(FilterInvocation fi) throws IOException, ServletException {
//        logger.info("进入invoke方法");
//        //if (fi != null)
//        //    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//        InterceptorStatusToken token = super.beforeInvocation(fi);
//        fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//        super.afterInvocation(token, null);
//    }
//
//    @Override
//    public void destroy() {
//        log.info("过滤器被销毁");
//    }
//
//    /**
//     * 被保护的对象，就是AccessDecisionManager 和 FilterInvocationMetaDataSource 的 Object 对象
//     *
//     * @return 被保护的对象
//     */
//    @Override
//    public Class<?> getSecureObjectClass() {
//        return FilterInvocation.class;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//        return metaDataSource;
//    }
//
//    @Autowired
//    public void setAccessDecisionManager(CusAccessDecisionManager accessDecisionManager) {
//        super.setAccessDecisionManager(accessDecisionManager);
//    }
//}
