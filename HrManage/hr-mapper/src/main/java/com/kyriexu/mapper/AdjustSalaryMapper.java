package com.kyriexu.mapper;

import com.kyriexu.model.AdjustSalary;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface AdjustSalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdjustSalary record);

    int insertSelective(AdjustSalary record);

    AdjustSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdjustSalary record);

    int updateByPrimaryKey(AdjustSalary record);
}