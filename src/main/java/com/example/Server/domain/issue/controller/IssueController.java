package com.example.Server.domain.issue.controller;

import com.example.Server.domain.challenge.dto.response.CreateChallengeResDto;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.service.IssuesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final IssuesService issuesService;

    @PostMapping("/create")
    public ResponseEntity<CreateChallengeResDto> createIssue(
            @RequestBody CreateIssueReqDto dto
            ) {

//        CreateIssueReqDto dto = CreateIssueReqDto.builder()
//                .content(content)
//                .category(category).build();

        return ResponseEntity.ok(issuesService.createIssue(dto));

    }
}
