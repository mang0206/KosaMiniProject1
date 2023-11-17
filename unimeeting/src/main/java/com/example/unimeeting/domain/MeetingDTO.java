package com.example.unimeeting.domain;


import lombok.Data;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@Data
public class MeetingDTO {
    private int idx;
    private String title;
    private String category;
    private String location;
    private String start_datetime;
    private String created_datetime;
    private int recruits;
    private String content;
    private String[] content_img;
    private String writer_nickname;
}
