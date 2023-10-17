package com.example.unimeeting.domain;

import lombok.*;

@Getter
@Setter
public class MeetingMemberDTO {
    int idx;
    @NonNull
    int meeting_idx;
    @NonNull
    int user_idx;
    boolean accepted;

    public MeetingMemberDTO(int meeting_idx, int user_idx ){
        this.meeting_idx = meeting_idx;
        this.user_idx = user_idx;
    }
}
