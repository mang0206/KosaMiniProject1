package com.example.unimeeting.domain;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/*
* Mypage의 생성한 소모임, 참여한 소모임, 스크랩 소모임 분류를 위해
* 어떤 분류의 소모임인지에 대한 division 맴버 변수와
* 그 분류에 대한 미팅 리스트에 대한 list 맴버 변수를 가지고 있는 DTO
*/
@Getter
@Setter
public class MyInfoMeetingDTO {
  private String division;
  private List<MeetingJoinDTO> list;
}
