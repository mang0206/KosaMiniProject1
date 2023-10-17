package com.example.unimeeting.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InfoDTO {
    private String id; //
    private String nickname; //닉네임
    private String title;
    private String category; //분류
    private String content_text; //글 내용
    private int recruits; //모집인원
    private String start_datetime;
    private String end_datetime;
}
