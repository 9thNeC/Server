package com.example.Server.infra.s3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlReqDto {
    private String fileName;
    private String contentType;
}
