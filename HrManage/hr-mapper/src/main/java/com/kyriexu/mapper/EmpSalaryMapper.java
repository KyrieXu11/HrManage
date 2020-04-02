package com.kyriexu.mapper;

import com.kyriexu.model.EmpSalary;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface EmpSalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpSalary record);

    int insertSelective(EmpSalary record);

    EmpSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpSalary record);

    int updateByPrimaryKey(EmpSalary record);
}