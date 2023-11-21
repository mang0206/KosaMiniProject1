package com.example.unimeeting.repository;

import com.example.unimeeting.domain.MeetingImage;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingImageRepository extends JpaRepository<MeetingImage, Integer> {

//   public List<String> findImageUrlByMeetingIdx(Integer idx);
//    image_url 의 list만 가져오는 것 불가능
    @Query("select i.imageUrl from MeetingImage i where i.meetingIdx = :idx")
    public List<String> findImageUrlByMeetingIdx(@Param("idx") Integer idx);
}
