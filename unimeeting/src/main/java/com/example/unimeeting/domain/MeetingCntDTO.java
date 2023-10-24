package com.example.unimeeting.domain;

import lombok.Data;

@Data
public class MeetingCntDTO {
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
