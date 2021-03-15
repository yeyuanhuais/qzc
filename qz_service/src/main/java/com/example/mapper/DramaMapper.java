package com.example.mapper;

import com.example.po.Drama;
import com.example.po.DramaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DramaMapper {
    int countByExample(DramaExample example);

    int deleteByExample(DramaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Drama record);

    int insertSelective(Drama record);

    List<Drama> selectByExample(DramaExample example);

    Drama selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Drama record, @Param("example") DramaExample example);

    int updateByExample(@Param("record") Drama record, @Param("example") DramaExample example);

    int updateByPrimaryKeySelective(Drama record);

    int updateByPrimaryKey(Drama record);
}