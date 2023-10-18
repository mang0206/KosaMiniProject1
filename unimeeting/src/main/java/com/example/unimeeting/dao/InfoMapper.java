package com.example.unimeeting.dao;

import com.example.unimeeting.domain.InfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper {
    @Select("select * from meeting left join meeting_image on meeting.idx = meeting_image.meeting_idx group by meeting.idx") //전체
    public List<InfoDTO> listM();

    @Select("select * from meeting where content_text like concat('%',#{key},'%') or title like concat('%',#{key},'%')") //검색
    public List<InfoDTO> searchM1(String keyword);

    @Select("select * from ") // 인기순

    @Select("select * from meeting left join meeting_image on meeting.idx = meeting_image.meeting_idx group by meeting.idx order by binary(title)") // 제목순
    public List<InfoDTO> titleSort();


    @Select("select * from meeting left join meeting_image on meeting.idx = meeting_image.meeting_idx group by meeting.idx order by binary(title) order by binary(start_datetime) desc ") //최신순
    public List<InfoDTO> startDate();

//    @Select("")
//    @Select("select ")




}
