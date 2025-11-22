package com.example.Server.domain.issue.service;

import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.dto.res.AiResponseResDto;

public interface AiResponseService {
    AiResponseResDto requestOpenAI(CreateIssueReqDto dto);
}
