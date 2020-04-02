package com.kyriexu.mapper;

import org.apache.ibatis.annotations.Param;
import com.kyriexu.model.Position;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/24 10:03
 **/
public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> getAllPositions();

    Integer deletePositionsByIds(@Param("ids") Integer[] ids);
}