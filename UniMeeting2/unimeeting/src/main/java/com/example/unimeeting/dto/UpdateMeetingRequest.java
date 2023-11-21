package com.example.unimeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UpdateMeetingRequest {
    private String title;
    private String content;
    private String category;
    private Integer recruits;
    private String location;
    private LocalDateTime startDatetime;
}
