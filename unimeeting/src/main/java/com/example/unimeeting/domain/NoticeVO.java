package com.example.unimeeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeVO {
  private int idx;
  private String title;
  private String type;
  private String content_text;
  private String created_datetime;
  private String writer_nickname;
}
