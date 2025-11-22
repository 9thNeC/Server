package com.example.Server.domain.challenge.dto.response;

import com.example.Server.domain.challenge.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeListItemDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String category;
    private LocalDateTime createdAt;

    public static ChallengeListItemDto from(Challenge challenge){
        ChallengeListItemDto dto = new ChallengeListItemDto();
        dto.id = challenge.getId();
        dto.title = challenge.getTitle();
        dto.imageUrl = challenge.getImageUrl();
        dto.category = challenge.getIssue().getCategory().toString();
        dto.createdAt = challenge.getCreatedAt();
        return dto;
    }
}