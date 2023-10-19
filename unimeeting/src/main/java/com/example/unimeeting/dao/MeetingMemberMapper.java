package com.example.unimeeting.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MeetingMemberMapper {
    @Select("select count(*) from meeting_member where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public int checkMetMem(int meeting_idx, int user_idx);
    
    @Insert("insert into meeting_member (meeting_idx, user_idx, accepted) values (#{meeting_idx} , #{user_idx}, 0)")
    public void insertMetMem(int meeting_idx, int user_idx);

    @Delete("delete from meeting_member where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean deleteMetMem(int meeting_idx, int user_idx);

    @Update("update meeting_member set accepted=true where meeting_idx=#{idx} and user_idx=#{user_idx}")
    public boolean updateApplyMetMem(int meeting_idx, int user_idx);
}
