package com.example.unimeeting.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "scrap")
@Getter
@Setter
@ToString
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "meeting_idx")
//    private Meeting meeting;
    @Column(name = "meeting_idx")
    private Integer meetingIdx;
}
