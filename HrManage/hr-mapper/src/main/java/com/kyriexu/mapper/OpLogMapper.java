package com.kyriexu.mapper;

import com.kyriexu.model.OpLog;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface OpLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OpLog record);

    int insertSelective(OpLog record);

    OpLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpLog record);

    int updateByPrimaryKey(OpLog record);
}