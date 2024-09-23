package com.metroflow.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "favoritelist")
public class FavoriteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long myfavNo;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stationId", referencedColumnName = "stationId", nullable = false)
    private SubwayStation station;

}