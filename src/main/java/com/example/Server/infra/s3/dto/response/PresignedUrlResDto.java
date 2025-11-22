package com.example.Server.infra.s3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlResDto {
    private String presignedUrl;
    private String fileKey;
}
