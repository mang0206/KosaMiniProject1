package com.example.unimeeting.dao;

import com.example.unimeeting.domain.CloudtypeUserVO;
import com.example.unimeeting.domain.MeetingVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MypageMapper {
  @Select("select idx, title, category, location, start_datetime, content_text, created_datetime,writer_nickname, recruits "
      + "from meeting where idx in (select idx from meeting_member where user_idx = #{idx})")
  public List<MeetingVO> attendList(CloudtypeUserVO userVO);

  @Select("select idx, title, category, location, start_datetime, content_text, created_datetime,writer_nickname, recruits"
      + " from meeting where writer_nickname = #{nickname}")
  public List<MeetingVO> createList(CloudtypeUserVO userVO);

  @Select("select idx, title, category, location, start_datetime, content_text, created_datetime,writer_nickname, recruits"
      + " from meeting where idx in (select meeting_idx from scrap where user_idx = #{idx})")
  public List<MeetingVO> scrapList(CloudtypeUserVO userVO);
}
