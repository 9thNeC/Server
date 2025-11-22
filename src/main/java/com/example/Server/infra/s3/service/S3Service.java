package com.example.Server.infra.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.Server.global.util.MemberUtil;
import com.example.Server.infra.s3.config.S3Properties;
import com.example.Server.infra.s3.dto.request.PresignedUrlReqDto;
import com.example.Server.infra.s3.dto.response.PresignedUrlResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;
    private final FileService fileService;

    public PresignedUrlResDto generatePresignedUrl(PresignedUrlReqDto reqDto) {

        Long memberId = 1L;//MemberUtil.getMember().getId();

        // 최종 S3에 저장될 키 = memberId/profile.png
        String fileKey = memberId + "/" + reqDto.getFileName();

        // Presigned URL 요청 생성
        GeneratePresignedUrlRequest request =
                fileService.createPresignedUrlRequest(
                        s3Properties.getBucket(),
                        fileKey,
                        reqDto.getContentType()
                );

        // Presigned URL 생성
        String presignedUrl = amazonS3.generatePresignedUrl(request).toString();

        // S3 실제 접근 URL (PUT 후 여기로 접근됨)
        String fileUrl = String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                s3Properties.getBucket(),
                s3Properties.getRegion(),
                fileKey
        );

        return new PresignedUrlResDto(presignedUrl, fileUrl);
    }

    public String generateDownloadPresignedUrl(String fileKey) throws FileNotFoundException {
        if (fileKey == null || fileKey.isBlank()) {
            throw new IllegalArgumentException("fileKey must not be empty.");
        }


        if (!amazonS3.doesObjectExist(s3Properties.getBucket(), fileKey)) {
            throw new FileNotFoundException("File not found in bucket: " + fileKey);
        }


        // GET 요청용 Presigned URL 생성
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
                s3Properties.getBucket(),
                fileKey
        ).withMethod(HttpMethod.GET)
                .withExpiration(fileService.getPresignedUrlExpiration());

        return amazonS3.generatePresignedUrl(request).toString();
    }

}

