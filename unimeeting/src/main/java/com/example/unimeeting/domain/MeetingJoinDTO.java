package com.example.unimeeting.domain;

import lombok.Data;

/*
* Meeting 정보에서 현재 참여 인원과 이미지 정보를 추가로 담기 위한 DTO
* 이미지의 경우 여러개 있다면 그 중 하나만 담는다
*/
@Data
public class MeetingJoinDTO {
  private int idx;
  private String title;
  private String category;
  private String location;
  private String start_datetime;
  private String created_datetime;
  private int now_recruits;
  private int recruits;
  private String content_text;
  private String writer_nickname;
  private String img_url;
}
