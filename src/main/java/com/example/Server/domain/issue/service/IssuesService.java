package com.example.Server.domain.issue.service;

import com.example.Server.domain.challenge.dto.res.CreateChallengeResDto;
import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;

public interface IssuesService {

    CreateChallengeResDto createIssue(CreateIssueReqDto dto);
}
