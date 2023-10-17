package com.example.unimeeting.dao;

import com.example.unimeeting.domain.InfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper {
    @Select("select * from meeting")
    public List<InfoDTO> listM();
    @Select("select * from meeting where content_text like concat('%',#{key},'%') or title like concat('%',#{key},'%')")
    public List<InfoDTO> searchM1(String keyword);

//    @Select("select * from meeting order by start_date")
//    public List<InfoDTO> dddd();


}
