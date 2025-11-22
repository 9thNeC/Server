package com.example.Server.domain.challenge.entity;

import com.example.Server.domain.member.entity.Member;
import com.example.Server.global.type.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

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

    // FK: member_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 고민 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 생성일
    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private LocalDate createAt;

    // 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
}
