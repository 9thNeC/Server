package com.example.Server.global.oauth.member.domain;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String socialId;   // 카카오 sub 값

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;         // USER 고정
}
