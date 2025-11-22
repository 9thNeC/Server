package com.example.Server.domain.challenge.controller;

import com.example.Server.domain.challenge.dto.response.ChallengeListResDto;
import com.example.Server.domain.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            summary = "챌린지 목록 조회",
            description = """
        회원의 챌린지 목록을 조회합니다.  
        category 값이 없으면 전체 챌린지를 반환하며,  
        category 값이 유효한 경우 해당 카테고리의 챌린지만 필터링하여 반환합니다.
        
        사용 가능한 category 값:
        STUDY, JOB, FAMILY, CAREER, HEALTH, RELATION, FINANCE, ETC
    """
    )
    @GetMapping
    public ChallengeListResDto challengeList(
            @Parameter(
                    description = "필터링할 카테고리 (없으면 전체 조회). 예: STUDY, JOB, FAMILY",
                    schema = @Schema(type = "string")
            )
            @RequestParam(defaultValue = "null") String category) {
        return challengeService.challengeList(category);
    }


}
