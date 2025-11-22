package com.example.Server.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameRequestDto {

    @Schema(description = "변경할 닉네임 (2~7자 한글/영문/숫자)", example = "슬픔 호소인")
    private String nickname;
}
