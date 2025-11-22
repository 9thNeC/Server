package com.example.Server.domain.challenge.entity;

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

    @Builder.Default
    private boolean status = false; // 성공 여부

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "comfort_content", length = 1000)
    private String comfortContent;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    Issue issue;

    public void update(String imageUrl) {
        this.imageUrl = imageUrl;
        this.status = true;
    }
}