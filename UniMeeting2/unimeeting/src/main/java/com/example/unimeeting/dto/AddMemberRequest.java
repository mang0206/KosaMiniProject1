package com.example.unimeeting.dto;

import com.example.unimeeting.domain.Member;
import com.example.unimeeting.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddMemberRequest {
    private Integer meeting_idx;
    private User user;

    public Member toEntity(){
        return Member.builder()
                .meetingIdx(meeting_idx)
                .user(user)
                .build();
    }

}
