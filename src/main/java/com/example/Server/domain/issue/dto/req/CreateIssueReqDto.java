package com.example.Server.domain.issue.dto.req;

import com.example.Server.global.type.Category;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class CreateIssueReqDto {
    private String content;
    private Category category;

}
