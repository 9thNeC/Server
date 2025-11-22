package com.example.Server.domain.issue.controller;

import com.example.Server.domain.challenge.dto.response.CreateChallengeResDto;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.global.common.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IssueControllerDocs {

    @Operation(
            summary = " 클라이언트 Issue 삽입 ",
            description = " 클라이언트의 고민으로 AI의 응답을 받는 api"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue, Challenge 삽입 및 AI 응답 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateChallengeResDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "닉네임 미설정",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한이 없는 사용자 접근",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "AI 응답 파싱 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<CreateChallengeResDto> createIssue(
            @RequestBody CreateIssueReqDto dto
    );
}
