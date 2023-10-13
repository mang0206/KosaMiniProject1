package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MeetingMapper {
    @Select("select distinct category from meeting order by category")
    public List<String> viewCtgy();

    @Select("<script>select * from meeting" +
            "<where>" +
            "<if test='category!=null'>category = #{category} </if>" +
            "<if test='search!=null'>title like concat('%',#{search}, '%')</if>" +
            "</where>order by idx</script>")
    public List<MeetingDTO> viewMetBoard(String category, String search);

    @Select("select count(*) from meeting")
    public int countMet();
}
