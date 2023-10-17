package com.example.unimeeting.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScrapMapper {

    @Insert("insert into scrap (meeting_idx, user_idx) value (#{meeting_idx}, #{user_idx})")
    public boolean insertScrap(int meeting_idx, int user_idx);

    @Delete("delete from scrap where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean deleteScrap(int meeting_idx, int user_idx);
}
