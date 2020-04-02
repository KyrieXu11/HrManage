package com.kyriexu.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author KyrieXu
 * @since 2020/3/28 13:42
 **/
@Component
@Slf4j
public class CusAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes==null)
            throw new AccessDeniedException("访问被拒绝，请联系管理员");
        // 这个集合就是metadatasource返回的需要的角色列表
        for (ConfigAttribute attribute : configAttributes) {
            // 获取需要的角色
            String needRole = attribute.getAttribute();
            if ("ROLE_login".equals(needRole)){
                // 如果是未登陆对象
                if (authentication instanceof AnonymousAuthenticationToken)
                    // 抛出异常
                    throw new AccessDeniedException("请登陆");
                else
                    return;
            }
            // 游客的话那么就直接过
            if ("ROLE_visitor".equals(needRole))
                return;
            // 获取已经认证的权限
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                // 获取权限的角色
                if (needRole.equals(authority.getAuthority()))
                    // 找到就返回
                    return;
            }
        }
        // 遍历完都还没找到权限
        throw new AccessDeniedException("访问被拒绝，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
