package com.example.Server.domain.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeListResDto {
    private String nickname;
    private List<ChallengeListItemDto> challenges;
}
