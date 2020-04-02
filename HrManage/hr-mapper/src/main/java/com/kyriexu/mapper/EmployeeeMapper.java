package com.kyriexu.mapper;

import com.kyriexu.model.Employeeec;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface EmployeeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeec record);

    int insertSelective(Employeeec record);

    Employeeec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeec record);

    int updateByPrimaryKey(Employeeec record);
}