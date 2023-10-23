package com.example.unimeeting.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface ScrapMapper {
    @Select("select count(*) from scrap where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public int checkScrap(@Param("meeting_idx") int meeting_idx, @Param("user_idx") int user_idx);

    @Insert("insert into scrap (meeting_idx, user_idx) value (#{meeting_idx}, #{user_idx})")
    public boolean insertScrap(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);

    @Delete("delete from scrap where meeting_idx=#{meeting_idx} and user_idx=#{user_idx}")
    public boolean deleteScrap(@Param("meeting_idx") int meeting_idx,@Param("user_idx") int user_idx);
}
