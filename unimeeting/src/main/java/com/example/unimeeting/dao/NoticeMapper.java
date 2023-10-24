package com.example.unimeeting.dao;

import com.example.unimeeting.domain.NoticeVO;
import org.apache.ibatis.annotations.*;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public interface NoticeMapper {

    @Select("SELECT * FROM board where type=#{type} ORDER BY created_datetime DESC")
    public List<NoticeVO> selectList(String type);
    //글 타입.

    @Insert("INSERT INTO board (title,content_text,type,created_datetime,writer_nickname) VALUES (#{title},#{content_text},#{type},now(),#{writer_nickname})")
    public void insertNotice(NoticeVO notice);

    @Select("SELECT * FROM board WHERE idx=#{idx}")
    public NoticeVO selectNotice(Integer idx);

    @Delete("DELETE FROM board WHERE idx=#{idx}")
    public void deleteNotice(Integer idx);

    @Update("UPDATE board set title=#{title} ,content_text=#{content_text} WHERE idx=#{idx}")
    public void updateNotice(NoticeVO notice);

    @Select("SELECT COUNT(*) FROM board where idx=#{idx} and writer_nickname=#{writer_nickname}")
    public int isWriter(int idx,String writer_nickname);
}
