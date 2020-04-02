package com.kyriexu.mapper;

import com.kyriexu.model.Salary;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface SalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Salary record);

    int insertSelective(Salary record);

    Salary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Salary record);

    int updateByPrimaryKey(Salary record);

    List<Salary> getAllSalaries();
}