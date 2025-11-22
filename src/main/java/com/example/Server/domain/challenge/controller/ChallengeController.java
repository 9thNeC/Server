package com.example.Server.domain.challenge.controller;

import com.example.Server.domain.challenge.dto.response.ChallengeListResDto;
import com.example.Server.domain.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping
    public ChallengeListResDto challengeList(@RequestParam(defaultValue = "null") String category) {
        return challengeService.challengeList(category);
    }
}
