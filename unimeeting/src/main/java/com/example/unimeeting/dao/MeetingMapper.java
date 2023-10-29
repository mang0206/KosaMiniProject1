package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingApplicantVO;
import com.example.unimeeting.domain.MeetingCntDTO;
import com.example.unimeeting.domain.MeetingDTO;
import com.example.unimeeting.domain.MeetingImageDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MeetingMapper {

    // 현재 meeting table에 존재하는 category들을 가져온다 (중복 제거)
    @Select("select distinct category from meeting order by category")
    public List<String> viewCtgy();

    // pagination 시 몇 개의 page
    @Select("<script>select count(*) from meeting <where>" +
            "<if test='category!=null'>category like #{category}</if>" +
            "<if test='search!=null'>and title like concat('%',#{search}, '%')</if>" +
            "</where></script>")
    public int cntMet(@Param("category") String category,@Param("search") String search);

    @Select("<script>select * , image_url as img_url " +
            "from meeting m left join ( select meeting_idx, count(*) as now_recruits " +
            "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx " +
            "left join ( select meeting_idx, image_url from meeting_image group by meeting_idx ) mi on m.idx = mi.meeting_idx " +
            "<where>" +
            "<if test='category!=null'>category like #{category} </if>" +
            "<if test='search!=null'>and title like concat('%',#{search}, '%')</if>" +
            "</where>order by idx desc </script>")
    public List<MeetingCntDTO> viewMetBoard(@Param("category") String category,@Param("search") String search);

    // Insert Meeting
    @Insert("insert into meeting (title, category, location, start_datetime, created_datetime, content_text,writer_nickname, recruits) " +
            "values (#{title}, #{category}, #{location}, #{start_datetime}, now() , #{content_text}, #{writer_nickname}, #{recruits})")
    public boolean insertMet(MeetingDTO meetingDTO);

    @Select("select max(idx) from meeting")
    public int getIdxOfCurrentMet();
    // View Meeting Post
    @Select("select * from meeting where idx = ${idx}")
    public MeetingDTO viewMetPost(@Param("idx") int idx);
    // Count Meeting member
    @Select("select count(*) from meeting_member where meeting_idx = ${idx}")
    public int countMetMem(@Param("idx") int idx);

    @Delete("delete from meeting where idx=#{idx}")
    public boolean deleteMeeting(@Param("idx") int idx);

    @Update("update meeting set title=#{title}, category=#{category}, location=#{location}, start_datetime=#{start_datetime}, content_text=#{content_text},recruits=#{recruits} where idx=#{idx} and writer_nickname=#{writer_nickname}")
    public boolean updateMet(MeetingDTO meetingDTO);

    @Select("select count(*) from meeting where idx=#{idx} && writer_nickname=#{writer_nickname}")
    public int isWriter(@Param("idx") int idx,@Param("writer_nickname")  String writer_nickname);

    @Insert("insert into meeting_image (meeting_idx, image_url) values (#{meeting_idx}, #{image_url})")
    public boolean insertMetImg(MeetingImageDTO meetingImageDTO);

    @Select("select image_url from meeting_image where meeting_idx=#{meeting_idx}")
    public String[] selectMetImg(int meeting_idx);

    @Select("select count(*) from meeting_member where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public int checkMetMem(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Select("select meeting_idx, user_idx, accepted, u.nickname, u.category from meeting_member m join user u on m.user_idx = u.idx where meeting_idx=${meeting_idx}")
    public List<MeetingApplicantVO> selectMetMem(@Param("meeting_idx") int meeting_idx);
    @Insert("insert into meeting_member (meeting_idx, user_idx, accepted) values (#{meeting_idx} , #{user_idx}, 0)")
    public void insertMetMem(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Delete("delete from meeting_member where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean deleteMetMem(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Update("update meeting_member set accepted=true where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean acceptApply(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Update("update meeting_member set accepted=false where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean acceptCancel(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Select("select count(*) from scrap where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public int checkScrap(@Param("meeting_idx") int meeting_idx, @Param("user_idx") int user_idx);

    @Insert("insert into scrap (meeting_idx, user_idx) value (#{meeting_idx}, #{user_idx})")
    public boolean insertScrap(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Delete("delete from scrap where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean deleteScrap(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

}
