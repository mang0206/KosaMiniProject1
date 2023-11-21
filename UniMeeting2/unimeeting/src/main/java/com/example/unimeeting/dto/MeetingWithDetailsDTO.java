package com.example.unimeeting.dto;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MeetingWithDetailsDTO {

  private Integer idx;
  private String title;
  private String content;
  private String category;
  private Integer recruits;
  private String location;
  private String writerNickname;
  private int memberNowRecruits;  // Member와의 연관성에서 나온 현재 참가자 수(현재 모임에 대한 참가자 수)
  private String imageUrl;            // MeetingImage와의 연관성에서 나온 이미지 URL(현재 모임에의 imageUrl 중 하나)

  public MeetingWithDetailsDTO(Meeting meeting, int cnt, String url) {
    this.idx = meeting.getIdx();
    this.title = meeting.getTitle();
    this.content = meeting.getContent();
    this.category = meeting.getCategory();
    this.recruits = meeting.getRecruits();
    this.location = meeting.getLocation();
    this.writerNickname = meeting.getUser().getNickname();  // Meeting과 User의 연관성에서 나온 작성자 닉네임
    this.memberNowRecruits = (cnt != 0) ? cnt : 0;  // Member가 없으면 0으로 처리
    this.imageUrl = url;
  }
}

