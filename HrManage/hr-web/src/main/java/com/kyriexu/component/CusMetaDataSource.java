package com.kyriexu.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultSatus;
import com.kyriexu.model.Hr;
import com.kyriexu.model.Menu;
import com.kyriexu.model.Role;
import com.kyriexu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/28 13:43
 **/
@Component
@Slf4j
public class CusMetaDataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    private AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 过滤器
     *
     * @param object filterinvocation对象
     * @return 需要的权限集合
     * @throws IllegalArgumentException 非法参数异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 强制转换
        FilterInvocation req = (FilterInvocation) object;
        // 获取当前访问的url
        String requestUrl = req.getRequestUrl();
        log.info(requestUrl);
        try {
            // 获取权限
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // 如果是匿名的话
            if (authentication instanceof AnonymousAuthenticationToken) {
                // 如果请求的url和/test*匹配的话，那么就不进行拦截，直接过
                if (matcher.match("/test*", requestUrl))
                    return SecurityConfig.createList("ROLE_visitor");
                // 如果不是访问的测试接口的话就返回登陆角色
                return SecurityConfig.createList("ROLE_login");
            }
            // 获取所有的菜单
            List<Menu> menus = menuService.getAllMenusWithRole();
            // 比较menu的url和当前用户访问的url
            for (Menu menu : menus) {
                // 如果匹配的话就
                if (matcher.match(menu.getUrl(), requestUrl)) {
                    // 获取访问对应的url需要的角色
                    List<Role> roles = menu.getRoles();
                    // 获取角色名称
                    String[] roleStr = new String[roles.size()];
                    for (int i = 0; i < roles.size(); i++) {
                        roleStr[i] = roles.get(i).getName();
                    }
                    // 返回需要的角色
                    return SecurityConfig.createList(roleStr);
                }
            }
        }
        catch (JsonProcessingException e) {
            log.info("json转换异常");
            throw new BaseException(ResultSatus.JSON_PROCESS_EXCEPTION);
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
