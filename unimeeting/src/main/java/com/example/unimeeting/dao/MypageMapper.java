package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingVO;
import com.example.unimeeting.domain.UpdateUserVO;
import java.util.List;

import com.example.unimeeting.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MypageMapper {
  @Select("select idx, title, category, location, start_datetime, content_text, created_datetime,writer_nickname, recruits "
      + "from meeting where idx in (select idx from meeting_member where user_idx = #{idx})")
  public List<MeetingVO> attendList(UserVO userVO);

  @Select("select idx, title, category, location, start_datetime, content_text, created_datetime,writer_nickname, recruits"
      + " from meeting where writer_nickname = #{nickname}")
  public List<MeetingVO> createList(UserVO userVO);

  @Select("select idx, title, category, location, start_datetime, content_text, created_datetime,writer_nickname, recruits"
      + " from meeting where idx in (select meeting_idx from scrap where user_idx = #{idx})")
  public List<MeetingVO> scrapList(UserVO userVO);

  @Update("update user set password = #{password}, nickname = #{nickname}, category = #{category} where idx = #{idx}")
  public boolean updateUser(UserVO userVO);
}
