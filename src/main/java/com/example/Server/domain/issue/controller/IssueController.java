package com.example.Server.domain.issue.controller;

import com.example.Server.domain.challenge.dto.res.CreateChallengeResDto;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.entity.Issue;
import com.example.Server.domain.issue.enums.Category;
import com.example.Server.domain.issue.service.IssuesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
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
