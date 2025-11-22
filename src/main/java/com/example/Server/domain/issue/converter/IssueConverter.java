package com.example.Server.domain.issue.converter;

import com.example.Server.domain.issue.dto.req.CreateIssueReqDto;
import com.example.Server.domain.issue.entity.Issue;
import org.springframework.stereotype.Component;

@Component
public class IssueConverter {

    public Issue toReivew(CreateIssueReqDto dto){
        return Issue.builder()

                .build();
    }
}
