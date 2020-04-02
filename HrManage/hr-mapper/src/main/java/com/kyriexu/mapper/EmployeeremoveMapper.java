package com.kyriexu.mapper;

import com.kyriexu.model.Employeeremove;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface EmployeeremoveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeremove record);

    int insertSelective(Employeeremove record);

    Employeeremove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeremove record);

    int updateByPrimaryKey(Employeeremove record);
}