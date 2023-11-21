package com.example.unimeeting.repository;
import com.example.unimeeting.domain.Scrap;
import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.dto.MeetingWithDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    // DB에 있는 meeting row의 category들을 중복 제외하고 List로 반환.
    // meeting page에 사용
    @Query("select distinct m.category from Meeting m order by m.category")
    public List<String> findDistinctCategoryBy();

    // 해당 category 내의 meeting글에서 검색
    @Query("select m from Meeting m  where m.category=:category and (title like %:title% or content like %:content%)")
    public List<Meeting> searchMeetingInCategory(@Param("category") String category, @Param("title") String title, @Param("content") String content);

    // idx의 글의 작성자가 user_nickname인지 확인
    // meeting 글 상세 보기 페이지에서 버튼
    public boolean existsByIdxAndUserNickname(int idx, String user_nickname);

    // 참여한 소모임 리스트
    @Query("select m from Meeting m where m.idx in (select mb.meetingIdx from Member mb where mb.user.idx = :idx)")
    public List<Meeting> searchMeetingInMemberIDX(int idx);

    // 생성한 소모임 리스트
    public List<Meeting> findByUserNickname(String nickname);

    // 스크랩한 소모임 리스트
    @Query("select m from Meeting m where m.idx in (select s.meetingIdx from Scrap s where s.user.idx = :idx)")
    public List<Meeting> searchMeetingInScrapIDX(int idx);

    // 모든 meeting글에서 검색 dh
    public List<Meeting> findAllByTitleContainingOrContentContaining(String title,String content); //검색어


    // main page에 사용
    // 모든 meeting글에서 검색
    @Query("select m from Meeting m where m.title like %:keyword% or m.content like %:keyword%")
    public List<Meeting> searchByList(@Param("keyword") String keyword); //검색어

    //인기순으로 정렬
//    @Query("select m from meeting m left join (select m.idx, count(*) from scrap s group by m.idx) " +
//            "as c on m.idx = c.meeting_idx order by scrap_cnt desc")
//    public List<Meeting> findAllByOrderByScrab();
    //제목순으로 정렬
    @Query("select m from Meeting m order by trim(m.title) asc")
    public List<Meeting> findAllByOrderByTitle();
    //시간순으로 정렬
    public List<Meeting> findAllByOrderByCreatedDatetimeDesc();

    //검색 결과만 정렬
//    @Query("select m from Meeting m left join Scrap s on m.idx = s.meetingIdx")
//    public List<Meeting> searchMeetingByOrderByScrab(String title,String content);
//    @Query("select m from Meeting m where m.title like %:keyword% or m.content like %:keyword% order by trim(m.title) asc")
//    public List<Meeting> searchMeetingByOrderByTitle(@Param("keyword") String keyword);
//    @Query("select m from Meeting m where m.title like %:keyword% or m.content like %:keyword% order by m.createdDatetime asc")
//    public List<Meeting> searchMeetingByOrderByCreatedDatetime(@Param("keyword") String keyword);

    //@Select("select m from meeting m left join (select m.idx, count(*) from scrap s group by m.idx) as c on m.idx = c.meeting_idx order by scrap_cnt desc") // 인기순




}
