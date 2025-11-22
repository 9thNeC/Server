package com.example.Server.domain.member.entity;

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

    private String nickname; //직접 입력받음.

    private String socialId;   // 카카오 sub 값

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;         // USER 고정
}
