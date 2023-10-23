package com.example.unimeeting.dao;

import com.example.unimeeting.domain.MeetingImageDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MeetingImageMapper {
    @Insert("insert into meeting_image (meeting_idx, image_url) values (#{meeting_idx}, #{image_url})")
    public boolean insertMetImg(MeetingImageDTO meetingImageDTO);

    @Select("select image_url from meeting_image where meeting_idx=#{meeting_idx}")
    public String[] selectMetImg(int meeting_idx);
}
