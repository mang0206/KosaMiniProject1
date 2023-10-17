package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingMemberDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MeetingMemberMapper {

    @Insert("insert into meeting_member (meeting_idx, user_idx, accepted) values (#{meeting_idx} , #{user_idx}, false)")
    public boolean insertMetMem(MeetingMemberDTO meetingMemberDTO);

    @Delete("delete from meeting_member where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean deleteMetMem(int meeting_idx, int user_idx);

    @Update("update meeting_member set accepted=true where meeting_idx=#{idx} and user_idx=#{user_idx}")
    public boolean updateApplyMetMem(int meeting_idx, int user_idx);
}
