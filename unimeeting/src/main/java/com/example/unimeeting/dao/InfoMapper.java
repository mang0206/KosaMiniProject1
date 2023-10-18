package com.example.unimeeting.dao;

import com.example.unimeeting.domain.InfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper {
    @Select("select * from meeting limit 5")
    public List<InfoDTO> listM();
    @Select("select * from meeting where content_text like concat('%',#{key},'%') or title like concat('%',#{key},'%')")
    public List<InfoDTO> searchM1(String keyword);

    @Select("select * from meeting order by binary(title)") // 이름순
    public List<InfoDTO> titleSort();

//    @Select("select * from ") // 관심순
    @Select("select * from meeting order by desc start_datetime") //최신순
    public List<InfoDTO> startDate();
//    @Select("")
//    @Select("select ")




}
