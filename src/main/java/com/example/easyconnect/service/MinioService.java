package com.example.easyconnect.service;

import com.example.easyconnect.exception.AppException;
import com.example.easyconnect.exception.ErrorCode;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioService {
    MinioClient minioClient;

    @NonFinal
    @Value("${minio.bucket-name}")
    protected String bucketName;

    @NonFinal
    @Value("${minio.url}")
    protected String minioUrl;

    public String uploadFile(String folder, MultipartFile file) {
        String fileName = folder + "/" + UUID.randomUUID();
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            return minioUrl + "/" + bucketName + "/" + fileName;
        }catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_UPLOAD_IMAGE);
        }
    }

    // Download to MinIO
    public InputStream downloadProfilePicture(String fileName) throws Exception {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (MinioException e) {
            throw new AppException(ErrorCode.ERROR_DOWNLOAD_IMAGE);
        }
    }
}
