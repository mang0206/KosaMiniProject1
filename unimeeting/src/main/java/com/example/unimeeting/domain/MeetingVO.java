package com.example.unimeeting.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MeetingVO {
  private int idx;
  private String title;
  private String category;
  private String location;
  private String start_datetime;
  private String content_text;
  private String created_datetime;
  private String writer_nickname;
  private int recruits;
}