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

/*
  CREATE TABLE IF NOT EXISTS `uni_meeting`.`board` (
    `idx` INT(11) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(45) NOT NULL,
    `type` VARCHAR(45) NOT NULL,
    `content_text` VARCHAR(500) NOT NULL,
    `content_img` VARCHAR(45) NULL DEFAULT NULL,
    `created_datetime` DATETIME NOT NULL,
    `writer_nickname` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idx`),
    INDEX `writer_nickname_idx` (`writer_nickname` ASC) VISIBLE,
    CONSTRAINT `user_board_nickname`
    FOREIGN KEY (`writer_nickname`)
    REFERENCES `uni_meeting`.`user` (`nickname`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;/*


 */