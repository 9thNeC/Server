package com.example.Server.domain.challenge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeVerificationReqDto {
    private String imageUrl;
}
