package com.kyriexu.mapper;

import com.kyriexu.model.Employeetrain;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface EmployeetrainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);
}