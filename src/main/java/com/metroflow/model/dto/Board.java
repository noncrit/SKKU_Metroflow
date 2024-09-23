package com.metroflow.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String boardText;
}