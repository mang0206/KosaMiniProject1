package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingJoinDTO;
import com.example.unimeeting.domain.MeetingVO;
import com.example.unimeeting.domain.UpdateUserVO;
import java.util.List;

import com.example.unimeeting.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MypageMapper {
/*  미팅 정보에서 현재 참여 인원과 이미지 정보를 추가
    현재 참여 인원을 구하기 위해 meeting_member 테이블을 join 하는데 join 하기전에 meeting_idx로 group 하고 count 집계함수를 사용해서 미팅에 대한 참여 인원을 구한다.
    이미지 정보도 join 하기전에 group 먼저 진행해서 하나만 가지고 혼다.
    meeting_member 테이블에서 uesr_idx 값이 로그인한 유저의 idx 값과 일치한 행의 meeting_idx값을 가지고 와서 in 연산을 통해 참여한 미팅 정보만 가지고 온다.
 */
  @Select("select m.idx, m.title, m.category, m.location, m.start_datetime, m.content_text, m.created_datetime, writer_nickname, m.recruits, now_recruits, image_url as img_url "
      + "from meeting m join ( select meeting_idx, count(*) as now_recruits "
      + "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx "
      + "left join ( select meeting_idx, image_url from meeting_image group by meeting_idx ) mi on m.idx = mi.meeting_idx "
      + "where m.idx in (select meeting_idx from meeting_member where user_idx = #{idx})")
  public List<MeetingJoinDTO> attendList(UserVO userVO);

  /*
    meeting 테이블에서 writer_nickname값이 로그인한 유저의 닉네임과 같은 행을 가지고 와서
    로그인한 유저가 생성한 미팅 정보만 가지고 온다.
   */
  @Select("select m.idx, m.title, m.category, m.location, m.start_datetime, m.content_text, m.created_datetime, writer_nickname, m.recruits, now_recruits, image_url as img_url "
      + "from meeting m left join ( select meeting_idx, count(*) as now_recruits "
      + "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx "
      + "left join ( select meeting_idx, image_url from meeting_image group by meeting_idx ) mi on m.idx = mi.meeting_idx "
      + "where writer_nickname in (#{nickname})")
  public List<MeetingJoinDTO> createList(UserVO userVO);

  /*
     scrap 테이블에서 uesr_idx 값이 로그인한 유저의 idx 값과 일치한 행의 meeting_idx값을 가지고 와서 in 연산을 통해 스크랩한 미팅 정보만 가지고 온다.
   */
  @Select("select m.idx, m.title, m.category, m.location, m.start_datetime, m.content_text, m.created_datetime, writer_nickname, m.recruits, now_recruits, image_url as img_url "
      + "from meeting m left join ( select meeting_idx, count(*) as now_recruits "
      + "from meeting_member group by meeting_idx ) mb on m.idx = mb.meeting_idx "
      + "left join ( select meeting_idx, image_url from meeting_image group by meeting_idx ) mi on m.idx = mi.meeting_idx "
      + "where idx in (select meeting_idx from scrap where user_idx = #{idx})")
  public List<MeetingJoinDTO> scrapList(UserVO userVO);

  /*
    user 정보에서 닉네임, Password, 카테고리를 Update 해준다.
   */
  @Update("update user set password = #{password}, nickname = #{nickname}, category = #{category} where idx = #{idx}")
  public boolean updateUser(UserVO userVO);
}
