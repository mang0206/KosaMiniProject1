package com.example.unimeeting.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name="meeting_image")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeetingImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "image_url")
    private String imageUrl;

//    @OneToOne
//    @JoinColumn(name = "meeting_idx")
    @Column(name = "meeting_idx")
    private Integer meetingIdx;
}
