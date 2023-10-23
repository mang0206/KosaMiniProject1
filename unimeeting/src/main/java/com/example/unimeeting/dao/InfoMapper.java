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

    @Select("select * from meeting as a left join (select meeting_idx, count(*) as scrap_cnt\n" +
            "from scrap group by meeting_idx) as c on a.idx = c.meeting_idx left join (select meeting_idx, image_url\n" +
            "from meeting_image group by meeting_idx) as b on c.meeting_idx = b.meeting_idx order by scrap_cnt desc") // 인기순
    public List<InfoDTO> popularSort();

    @Select("select * from meeting left join meeting_image on meeting.idx = meeting_image.meeting_idx group by meeting.idx order by binary(title)") // 제목순
    public List<InfoDTO> titleSort();


    @Select("select * from meeting left join meeting_image on meeting.idx = meeting_image.meeting_idx group by meeting.idx order by binary(created_datetime) desc ") //최신순
    public List<InfoDTO> createDate();

//    @Select("")
//    @Select("select ")




}
