package com.kyriexu.mapper;

import com.kyriexu.model.Appraise;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface AppraiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appraise record);

    int insertSelective(Appraise record);

    Appraise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appraise record);

    int updateByPrimaryKey(Appraise record);
}