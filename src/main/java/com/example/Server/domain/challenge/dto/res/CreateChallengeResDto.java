package com.example.Server.domain.challenge.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChallengeResDto {

    private String title;
    private String content;
    private String comfortContent;

}
