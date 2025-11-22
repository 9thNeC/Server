package com.example.Server.domain.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailChallengeResDto {
    private Long id;
    private String category;
    private String imageUrl;
    private String challengeTitle;
}
