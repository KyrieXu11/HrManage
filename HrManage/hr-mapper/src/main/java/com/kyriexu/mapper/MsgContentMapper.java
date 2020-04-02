package com.kyriexu.mapper;

import com.kyriexu.model.MsgContent;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface MsgContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MsgContent record);

    int insertSelective(MsgContent record);

    MsgContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MsgContent record);

    int updateByPrimaryKey(MsgContent record);
}