package com.example.unimeeting.dto;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class AddMeetingRequest {
    private String title;
    private String content;
    private String category;
    private Integer recruits;
    private String location;
    private LocalDateTime startDatetime;

    public Meeting toEntity(User user) {
        return Meeting.builder()
                .title(title)
                .content(content)
                .category(category)
                .recruits(recruits)
                .location(location)
                .startDatetime(startDatetime)
                .createdDatetime(LocalDateTime.now())
                .user(user)
                .build();
    }
}
