package com.example.unimeeting.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name="meeting")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String title;
    private String content;
    private String category;
    private Integer recruits;
    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH-mm")
    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH-mm")
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_nickname", referencedColumnName = "nickname")
    private User user;

    @Builder
    public Meeting(String title, String content, String category, Integer recruits,String location, LocalDateTime startDatetime, LocalDateTime createdDatetime, User user){
        this.title = title;
        this.content = content;
        this.category = category;
        this.recruits = recruits;
        this.location = location;
        this.startDatetime = startDatetime;
        this.createdDatetime = createdDatetime;
        this.user = user;
    }

    public void update(String title, String content, String category, Integer recruits,String location, LocalDateTime startDatetime) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.recruits = recruits;
        this.location = location;
        this.startDatetime = startDatetime;
    }

}

