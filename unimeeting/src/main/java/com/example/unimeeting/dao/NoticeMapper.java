package com.example.unimeeting.dao;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {

    @Select("SELECT * FROM board ORDER BY idx DESC")
    public List<NoticeVO> selectList();

    @Select("SELECT COUNT(*) FROM board b JOIN user u ON b.idx = u.idx")
    public int selectRowCount(Map<String,Object> map);



    @Insert("INSERT INTO board (title,content_text,type,created_datetime,writer_nickname) VALUES (#{title},#{content_text},#{type},now(),#{writer_nickname})")
    public void insertNotice(NoticeVO notice);

    @Select("SELECT * FROM board WHERE idx=#{idx}")
    public NoticeVO selectNotice(Integer idx);

    @Delete("DELETE FROM board WHERE idx=#{idx} and writer_nickname=#{writer_nickname}")
    public void deleteNotice(Integer idx ,String writer_nickname);


}
