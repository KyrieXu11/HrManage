package com.kyriexu.utils;

import com.kyriexu.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author KyrieXu
 * @since 2020/3/29 20:33
 **/
public class HrUtil {
    /**
     * 获取hr的id
     * @return hr.id
     */
    public static Hr getHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
