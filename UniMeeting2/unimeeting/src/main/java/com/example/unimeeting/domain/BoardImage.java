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
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "image_url")
    private String imageUrl;

//    @OneToOne
    @Column(name = "board_idx")
    private Integer boardIdx;
}
