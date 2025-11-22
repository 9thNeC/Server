package com.example.Server.domain.challenge.entity;

import com.example.Server.domain.issue.entity.Issue;
import com.example.Server.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "create_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "comfort_content", length = 1000)
    private String comfortContent;

//    @ManyToOne
//    Member member;

    @ManyToOne
    Issue issue;
}
