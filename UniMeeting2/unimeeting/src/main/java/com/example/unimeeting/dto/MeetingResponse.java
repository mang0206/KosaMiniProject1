package com.example.unimeeting.dto;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class MeetingResponse {

    private int idx;
    private String title;
    private String content;
    private String category;
    private Integer recruits;
    private String location;
    private LocalDateTime startDatetime;
    private LocalDateTime createdDatetime;
    private String writerNickname;

    private Integer currentRecruits;
    private List<String> images;
    private boolean isWriter;
    private boolean isApplicant;

    public MeetingResponse(Meeting meeting, Integer currentRecruits, List<String> images, Boolean isWriter, Boolean isApplicant){
        this.idx = meeting.getIdx();
        this.title = meeting.getTitle();
        this.content = meeting.getContent();
        this.category = meeting.getCategory();
        this.recruits = meeting.getRecruits();
        this.location = meeting.getLocation();
        this.startDatetime = meeting.getStartDatetime();
        this.createdDatetime = meeting.getCreatedDatetime();
        this.writerNickname = meeting.getUser().getNickname();

        this.currentRecruits = currentRecruits;
        this.images = images;
        this.isWriter = isWriter;
        this.isApplicant = isApplicant;

    }
}
