package com.kyriexu.mapper;

import com.kyriexu.model.Role;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getAllRoles();
}