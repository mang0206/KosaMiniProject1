package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingCntDTO;
import com.example.unimeeting.domain.MeetingVO;
import com.example.unimeeting.domain.UpdateUserVO;
import java.util.List;

import com.example.unimeeting.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MypageMapper {
  @Select("select m.idx, m.title, m.category, m.location, m.start_datetime, m.content_text, m.created_datetime, writer_nickname, m.recruits, now_recruits "
      + "from meeting m join ( select meeting_idx, count(*) as now_recruits "
      + "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx "
      + "where m.idx in (select meeting_idx from meeting_member where user_idx = #{idx})")
  public List<MeetingCntDTO> attendList(UserVO userVO);

  @Select("select m.idx, m.title, m.category, m.location, m.start_datetime, m.content_text, m.created_datetime, writer_nickname, m.recruits, now_recruits "
      + "from meeting m join ( select meeting_idx, count(*) as now_recruits "
      + "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx "
      + "where writer_nickname = #{nickname}")
  public List<MeetingCntDTO> createList(UserVO userVO);

  @Select("select m.idx, m.title, m.category, m.location, m.start_datetime, m.content_text, m.created_datetime, writer_nickname, m.recruits, now_recruits "
      + "from meeting m join ( select meeting_idx, count(*) as now_recruits "
      + "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx "
      + "where idx in (select meeting_idx from scrap where user_idx = #{idx})")
  public List<MeetingCntDTO> scrapList(UserVO userVO);

  @Update("update user set password = #{password}, nickname = #{nickname}, category = #{category} where idx = #{idx}")
  public boolean updateUser(UserVO userVO);
}
