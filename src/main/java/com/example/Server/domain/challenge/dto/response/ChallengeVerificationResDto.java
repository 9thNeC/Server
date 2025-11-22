package com.example.Server.domain.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVerificationResDto {
    private Long id;
    private String imageUrl;
    private String message;
}
