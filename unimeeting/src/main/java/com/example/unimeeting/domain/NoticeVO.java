package com.example.unimeeting.domain;

import java.sql.Date;
import lombok.Data;

@Data
public class NoticeVO {
    private int idx;
    private String title;
    private String content;
    private Date created_datetime;
    private String writer_nickname;
    private String type;//공지사항의 글인지 게시판의 글인지 구별하기위한 것.

}
