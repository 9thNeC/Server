package com.example.Server.oauth.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RedirectDto {
    private String code;   // 카카오 로그인 redirect 시 전달되는 인가코드
}