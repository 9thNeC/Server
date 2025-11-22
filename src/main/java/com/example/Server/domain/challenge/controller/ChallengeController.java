package com.example.Server.domain.challenge.controller;

import com.example.Server.domain.challenge.dto.response.ChallengeListResDto;
import com.example.Server.domain.challenge.dto.response.DetailChallengeResDto;
import com.example.Server.domain.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Tag(name = "Challenge API", description = "챌린지 관련 API")
    @GetMapping("/{challengeId}")
    @Operation(
            summary = "챌린지 상세조회",
            description = """
        특정 챌린지의 상세 정보를 조회합니다.
        challengeId가 정상적으로 존재해야 하며,
        존재하지 않거나 접근 권한이 없는 경우 예외가 발생합니다.
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 챌린지 상세 정보를 반환함",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailChallengeResDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 챌린지 ID"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한이 없는 사용자의 접근"
            )
    })
    public DetailChallengeResDto detailChallenge(
            @Parameter(description = "조회할 챌린지 ID", example = "12")
            @PathVariable("challengeId") Long challengeId
    ) {
        return challengeService.detailChallenge(challengeId);
    }


}
