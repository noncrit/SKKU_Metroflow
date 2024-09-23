package com.metroflow.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// 유저 엔티티
@Getter
@Setter
@Entity
@Table(name="USER")
public class User {
    @Id
    @Column(length = 15)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(length = 10, nullable = false)
    private String nickname;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String userImgPath;
    @Column(length = 50, nullable = false)
    private String userEmail;
    @ElementCollection
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userId", nullable = false)
    )
    @Column(length = 5)
    private Set<String> userRole;


}