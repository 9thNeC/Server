package com.example.Server.domain.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailChallengeWithIssueResDto {
    private Long id;
    private String nickname;
    private LocalDateTime createdAt;
    private String issueContent;
    private String comfortContent;
    private String challengeTitle;
    private String challengeContent;
}
