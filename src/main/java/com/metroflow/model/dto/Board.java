package com.metroflow.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String boardText;
    @Column(length = 15, nullable = false)
    private String stationName;
    @Column(length = 3, nullable = false)
    private String stationLine;
    @Column(length = 20, nullable = false)
    private String title;
    private LocalDateTime createdTime;
    @Column(columnDefinition = "bigint")
    private Long thumbsUp;
    @Column(columnDefinition = "bigint")
    private Long thumbsDown;
    @Column(columnDefinition = "bigint")
    private Long view;
}