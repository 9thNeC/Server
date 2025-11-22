package com.example.Server.domain.issue.controller;

import com.example.Server.domain.challenge.dto.response.ChallengeVerificationResDto;
import com.example.Server.domain.challenge.dto.response.CreateChallengeResDto;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.service.IssuesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController implements IssueControllerDocs{

    private final IssuesService issuesService;

    @PostMapping("/create")
    public ResponseEntity<CreateChallengeResDto> createIssue(
            @RequestBody CreateIssueReqDto dto
            ) {

        return ResponseEntity.ok(issuesService.createIssue(dto));

    }
}
