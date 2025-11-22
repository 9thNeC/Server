package com.example.Server.domain.challenge.controller;

import com.example.Server.domain.challenge.dto.request.ChallengeVerificationReqDto;
import com.example.Server.domain.challenge.dto.response.ChallengeListResDto;
import com.example.Server.domain.challenge.dto.response.ChallengeVerificationResDto;
import com.example.Server.domain.challenge.dto.response.DetailChallengeResDto;
import com.example.Server.domain.challenge.dto.response.DetailChallengeWithIssueResDto;
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
            summary = "챌린지 목록 조회"
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
            summary = "챌린지 상세조회"
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

    @Tag(name = "Challenge API", description = "챌린지 관련 API")
    @GetMapping("/{challengeId}/issue")
    @Operation(
            summary = "챌린지 + 고민 상세조회"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "챌린지 및 Issue 상세 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailChallengeWithIssueResDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 챌린지 ID"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한이 없는 사용자 접근"
            )
    })
    public DetailChallengeWithIssueResDto detailChallengeWithIssue(
            @Parameter(description = "조회할 챌린지 ID", example = "12")
            @PathVariable("challengeId") Long challengeId
    ) {
        return challengeService.detailChallengeWithIssue(challengeId);
    }

    @PatchMapping("/{challengeId}/verification")
    @Operation(
            summary = "챌린지 인증 처리"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "챌린지 인증 처리 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChallengeVerificationResDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 챌린지"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "해당 챌린지에 대한 권한 없음"
            )
    })
    public ChallengeVerificationResDto challengeVerification(
            @Parameter(description = "인증할 챌린지 ID", example = "12")
            @PathVariable("challengeId") Long challengeId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "챌린지 인증 요청 데이터",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ChallengeVerificationReqDto.class))
            )
            @RequestBody ChallengeVerificationReqDto reqDto
    ) {
        return challengeService.challengeVerification(challengeId, reqDto);
    }


}
