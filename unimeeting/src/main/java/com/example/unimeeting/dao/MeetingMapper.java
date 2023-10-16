package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MeetingMapper {

    // Category List
    @Select("select distinct category from meeting order by category")
    public List<String> viewCtgy();

    // Meeting List
    @Select("<script>select * from meeting" +
            "<where>" +
            "<if test='category!=null'>category = #{category} </if>" +
            "<if test='search!=null'>and title like concat('%',#{search}, '%')</if>" +
            "</where>order by idx desc limit 4 offset ${page}</script>")
    public List<MeetingDTO> viewMetBoard(@Param("category") String category,@Param("search") String search,@Param("page") int page );

    // Insert Meeting
    @Insert("insert into meeting (title, category, location, start_datetime, created_datetime, content_text, content_img,writer_nickname) " +
            "values (#{title}, #{category}, #{location}, #{start_datetime}, now() , #{content_text}, #{content_img}, #{writer_nickname})")
    public boolean insertMet(MeetingDTO meetingDTO);

    // View Meeting Post
    @Select("select * from meeting where idx = ${idx}")
    public MeetingDTO viewMetPost(@Param("idx") int idx);

    // Count Meeting member
    @Select("select count(*) from meeting_member where meeting_idx = ${idx}")
    public int countMetMem(@Param("idx") int idx);

    //    // page
//    @Select("<script>select count(*) from meeting"+
//            "<where>" +
//            "<if test='category!=null'>category = #{category} </if>" +
//            "<if test='search!=null'>title like concat('%',#{search}, '%')</if>" +
//            "</where></script>")
//    public int countMet(@Param("category") String category,@Param("search") String search);
}
