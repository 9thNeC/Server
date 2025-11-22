package com.example.Server.infra.s3.controller;

import com.example.Server.infra.s3.dto.request.PresignedUrlReqDto;
import com.example.Server.infra.s3.dto.response.ImageDownLoadResDto;
import com.example.Server.infra.s3.dto.response.PresignedUrlResDto;
import com.example.Server.infra.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @Operation(summary = "presigned url 발급", description = "챌린지 이미지 업로드용으로 presignedUrl 한개만 발급됨")
    @PostMapping(value = "/presign")
    public PresignedUrlResDto generatePresignedUrl(@RequestBody PresignedUrlReqDto reqDto) {
        return s3Service.generatePresignedUrl(reqDto);
    }

    @GetMapping("/download")
    @Operation(
            summary = "이미지 다운로드용 Presigned URL 발급",
            description = "S3에 저장된 특정 파일을 GET 요청으로 다운로드할 수 있는 Presigned URL을 발급합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presigned URL 발급 성공"),
            @ApiResponse(responseCode = "400", description = "요청 파라미터 오류"),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ImageDownLoadResDto generateDownloadUrl(
            @Parameter(
                    name = "fileKey",
                    description = "다운로드할 S3 파일 경로 (예: '1/profile.png')",
                    required = true,
                    example = "1/profile.png"
            )
            @RequestParam String fileKey
    ) throws FileNotFoundException {
        return new ImageDownLoadResDto(s3Service.generateDownloadPresignedUrl(fileKey));
    }

}
