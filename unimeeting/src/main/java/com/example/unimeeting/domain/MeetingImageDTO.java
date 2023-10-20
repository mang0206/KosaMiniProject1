package com.example.unimeeting.domain;

import lombok.*;

@Getter
@Setter
@ToString
public class MeetingImageDTO {
    private int idx;

    private int meeting_idx;
    private String image_url;

    public MeetingImageDTO(int meeting_idx, String image_url ){
        this.meeting_idx = meeting_idx;
        this.image_url = image_url;
    }
}
