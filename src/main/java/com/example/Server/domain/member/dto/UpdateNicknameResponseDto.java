package com.example.Server.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateNicknameResponseDto {

    @Schema(description = "변경된 닉네임", example = "슬픔 호소인")
    private String nickname;

    @Schema(description = "닉네임 설정 결과 메시지", example = "정상적으로 닉네임이 설정되었습니다.")
    private String message;
}
