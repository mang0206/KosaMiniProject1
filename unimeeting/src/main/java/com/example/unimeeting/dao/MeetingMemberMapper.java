package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingMemberDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetingMemberMapper {

    @Insert("insert into meeting_member (meeting_idx, user_idx, accepted) values (#{meeting_idx} , #{user_idx}, false)")
    public boolean insertMetMem(MeetingMemberDTO meetingMemberDTO);
}
