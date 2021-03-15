package com.example.mapper;

import com.example.po.Liked;
import com.example.po.LikedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LikedMapper {
    int countByExample(LikedExample example);

    int deleteByExample(LikedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Liked record);

    int insertSelective(Liked record);

    List<Liked> selectByExample(LikedExample example);

    Liked selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Liked record, @Param("example") LikedExample example);

    int updateByExample(@Param("record") Liked record, @Param("example") LikedExample example);

    int updateByPrimaryKeySelective(Liked record);

    int updateByPrimaryKey(Liked record);
}