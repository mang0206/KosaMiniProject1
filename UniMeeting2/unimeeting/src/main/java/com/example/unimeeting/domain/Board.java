package com.example.unimeeting.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String title;
    private String content;
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH-mm")
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @ManyToOne
    @JoinColumn(name = "writer_nickname", referencedColumnName = "nickname")
    private User user;

    @Builder
    public Board(String title, String content, String type){
        this.title = title;
        this.content = content;
        this.type = type;
        this.createdDatetime = LocalDateTime.now();
    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .type(type)
                .build();
    }

}
