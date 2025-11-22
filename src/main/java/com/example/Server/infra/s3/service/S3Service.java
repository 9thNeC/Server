package com.example.Server.infra.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.Server.infra.s3.config.S3Properties;
import com.example.Server.infra.s3.dto.response.PresignedUrlResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final FileService fileService;
    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    public PresignedUrlResDto generatePresignedUrl() {
        Long memberId = 1L;//MemberUtil.getMember().getId();
        String fileKey = fileService.generateUUID(); // UUID 기반 고유 파일 키 생성
        String fileName = fileService.createFileName(memberId, fileKey); // 사용자 ID와 UUID를 조합한 파일 이름 생성

        // Presigned URL 요청 객체 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                fileService.createGeneratePresignedUrlRequest(s3Properties.getBucket(), fileName);

        // Presigned URL 생성
        String presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return new PresignedUrlResDto(presignedUrl, fileKey);
    }

}
