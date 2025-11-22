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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    // 	파일 구분용 고유 UUID 생성
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

    // 사용자 ID + UUID 조합으로 S3에 저장할 파일 이름 생성
    public String createFileName(Long memberId, String imageKey) {
        return memberId
                + "/"
                + imageKey;
    }

    // 	Presigned URL 생성을 위한 요청 객체 구성
    public GeneratePresignedUrlRequest createGeneratePresignedUrlRequest(String bucket, String fileName) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withKey(fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPresignedUrlExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    // Presigned URL의 유효시간(5분) 설정
    public Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(5);
        expiration.setTime(expTime);

        return expiration;
    }

    // S3에서 해당 파일 삭제
    public void deleteFile(String fileUrl) {
        try {
            String bucket = s3Properties.getBucket();
            String fileKey = extractKeyFromUrl(fileUrl);
            amazonS3.deleteObject(bucket, fileKey);
            log.info("Deleted file from S3: {}", fileKey);
        } catch (Exception e) {
            log.error("Failed to delete file from S3: {}", fileUrl, e);
        }
    }

    // 	전체 URL에서 S3 파일 키만 추출
    private String extractKeyFromUrl(String fileUrl) {
        // presigned URL의 실제 파일 경로만 추출
        URI uri = URI.create(fileUrl);
        return uri.getPath().substring(1); // e.g., "1/f829333c-2e4f-4a38-8eb9-2ee10ae2117e"
    }
}
