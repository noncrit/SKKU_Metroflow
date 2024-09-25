package com.metroflow.model.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
public class BoardDTO {
    private long boardNo;
    private User user;
    private String boardText;
    private String stationName;
    private String stationLine;
    private String title;
    private LocalDateTime createdTime;
    private Long thumbsUp;
    private Long thumbsDown;
    private Long view;

    public BoardDTO(long boardNo, User user, String stationLine, String title, LocalDateTime createdTime, Long thumbsUp, Long view) {
        this.boardNo = boardNo;
        this.user = user;
        this.stationLine = stationLine;
        this.title = title;
        this.createdTime = createdTime;
        this.thumbsUp = thumbsUp;
        this.view = view;
    }
}
