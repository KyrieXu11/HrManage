package com.kyriexu.mapper;

import com.kyriexu.model.SysMsg;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface SysMsgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMsg record);

    int insertSelective(SysMsg record);

    SysMsg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMsg record);

    int updateByPrimaryKey(SysMsg record);
}