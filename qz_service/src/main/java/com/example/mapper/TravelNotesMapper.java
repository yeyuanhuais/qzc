package com.example.mapper;

import com.example.po.TravelNotes;
import com.example.po.TravelNotesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TravelNotesMapper {
    int countByExample(TravelNotesExample example);

    int deleteByExample(TravelNotesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TravelNotes record);

    int insertSelective(TravelNotes record);

    List<TravelNotes> selectByExample(TravelNotesExample example);

    TravelNotes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TravelNotes record, @Param("example") TravelNotesExample example);

    int updateByExample(@Param("record") TravelNotes record, @Param("example") TravelNotesExample example);

    int updateByPrimaryKeySelective(TravelNotes record);

    int updateByPrimaryKey(TravelNotes record);
}