package com.example.Server.infra.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.Server.infra.s3.config.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;

    // Presigned URL 요청 생성
    public GeneratePresignedUrlRequest createPresignedUrlRequest(
            String bucket,
            String fileKey,
            String contentType
    ) {
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucket, fileKey)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPresignedUrlExpiration());

        // Content-Type 설정 추가
        request.addRequestParameter("Content-Type", contentType);

        // 업로드된 파일을 공개로 만들고 싶으면 (선택)
        request.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return request;
    }

    // Presigned URL 유효시간 (5분)
    public Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + TimeUnit.MINUTES.toMillis(5));
        return expiration;
    }
}
