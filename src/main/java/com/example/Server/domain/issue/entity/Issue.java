package com.example.Server.domain.issue.entity;

import com.example.Server.domain.challenge.entity.Challenge;
import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.type.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 400)
    private String content;

    @Column(name = "create_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    Member member;

/*
    @OneToMany(mappedBy = "issue")
    private List<Challenge> challenges = new ArrayList<>();
*/

}


