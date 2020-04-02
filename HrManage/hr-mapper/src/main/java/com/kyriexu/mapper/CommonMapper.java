package com.kyriexu.mapper;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/28 13:00
 **/
public interface CommonMapper<T> {
    List<T> getAll();
}
