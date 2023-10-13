package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
            "</where>order by idx desc limit 4 offset ${page}</script>")
    public List<MeetingDTO> viewMetBoard(@Param("category") String category,@Param("search") String search,@Param("page") int page );


    @Insert("insert into book (title, category, location, start_datetime, created_datetime, content_text, content_img,writer_nickname) " +
            "values (#{title}, #{category}, #{location}, , #{created_datetime}, #{content_text}, #{content_img}, #{writer_nickname})")
    public boolean insertBook(MeetingDTO meetingDTO);
//    // page
//    @Select("<script>select count(*) from meeting"+
//            "<where>" +
//            "<if test='category!=null'>category = #{category} </if>" +
//            "<if test='search!=null'>title like concat('%',#{search}, '%')</if>" +
//            "</where></script>")
//    public int countMet(@Param("category") String category,@Param("search") String search);
}
