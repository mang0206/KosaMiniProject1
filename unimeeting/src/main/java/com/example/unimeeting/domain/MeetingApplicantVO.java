package com.example.unimeeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MeetingApplicantVO {
    private int meeting_idx;
    private int user_idx;
    private int accepted;
    private String nickname;
    private String category;
}
