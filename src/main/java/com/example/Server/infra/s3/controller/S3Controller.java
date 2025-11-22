package com.example.Server.infra.s3.controller;

import com.example.Server.infra.s3.dto.request.PresignedUrlReqDto;
import com.example.Server.infra.s3.dto.response.PresignedUrlResDto;
import com.example.Server.infra.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @Operation(summary = "presigned url 발급", description = "챌린지 이미지 업로드용으로 presignedUrl 한개만 발급됨")
    @PostMapping(value = "/presign")
    public PresignedUrlResDto generatePresignedUrl(PresignedUrlReqDto reqDto) {
        return s3Service.generatePresignedUrl(reqDto);
    }
}
