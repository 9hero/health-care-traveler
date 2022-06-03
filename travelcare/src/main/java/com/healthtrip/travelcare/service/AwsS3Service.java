package com.healthtrip.travelcare.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.healthtrip.travelcare.common.CommonUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFileV1(MultipartFile multipartFile, String fileName) throws Exception {
//        validateFileExists(multipartFile);
        if(multipartFile.isEmpty()){
            throw new Exception();
        }
//        String fileName = CommonUtils.buildFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()),directory);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
//            throw new FileUploadFailedException();
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
//            throw new EmptyFileException();
        }
    }

    public boolean deleteFileByNameList(Stream<String> nameList) {
        try{
        nameList.forEach(name ->
        amazonS3Client.deleteObject(bucketName,name)
        );
        return true;
        }catch (Exception e){
            return false; // 부분 성공댐 실패하면 남은부분 로그남겨서 제대로 제거해줘야함
            // 예를 들어 폴더에서 키 가져와서 db랑 비교후 없는거 걸러서 제거
            // 아니면 삭제 테이블 만들어서 거기로 파일정보 이전시킨후 s3와 비교후 삭제
        }
    }
}
