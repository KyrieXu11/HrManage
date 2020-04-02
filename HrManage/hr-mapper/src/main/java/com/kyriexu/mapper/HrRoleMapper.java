package com.kyriexu.mapper;

import org.apache.ibatis.annotations.Param;
import com.kyriexu.model.HrRole;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    void deleteByHrid(Integer hrid);

    Integer addRole(@Param("hrid") Integer hrid, @Param("rids") Integer[] rids);
}