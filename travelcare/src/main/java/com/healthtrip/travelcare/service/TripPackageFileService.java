package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.domain.entity.TripPackage;
import com.healthtrip.travelcare.domain.entity.TripPackageFile;
import com.healthtrip.travelcare.repository.TripPackageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TripPackageFileService {
    private final TripPackageFileRepository tripPackageFileRepository;
    private final AwsS3Service awsS3Service;

    private static final String IMAGE_DIRECTORY = "images/trip-package"; // 패키지 이미지폴더로 이동예정

    @Transactional
    public void uploadTripImage(List<MultipartFile> files, TripPackage tripPackage) throws Exception {
        List<TripPackageFile> tripPackageFiles = new ArrayList<>();

        // 이렇게되면 패키지 등록 실패해도 파일이 남아있음 실패하면
        // 이유 : 파일 먼저 생성,패키지 등록하기 때문
        // 패키지 먼저등록 -> 업데이트 =  기존: 파일생성 + 엔티티저장 = 2 연결
        // 앤티티저장 -> 파일생성 -> url 업데이트 = 3 연결
        // 패키지 등록 실패확률 vs 3연결
        // 고로 파일 널체크하고 들어감
        for (MultipartFile file:files){
            String fileName = CommonUtils.buildFileName(Objects.requireNonNull(file.getOriginalFilename()), IMAGE_DIRECTORY);
            String url = awsS3Service.uploadFileV1(file,fileName);

        tripPackageFiles.add(TripPackageFile.builder()
                .url(url)
                .fileSize(file.getSize())
                .fileName(fileName)
                .originalName(file.getOriginalFilename())
                .tripPackage(tripPackage)
                .build()
                );
        }
        //saveAll
        tripPackageFileRepository.saveAll(tripPackageFiles);
        // updateAll
    }

    @Transactional
    public void getData() {
    }
}
