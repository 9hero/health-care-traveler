package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import com.healthtrip.travelcare.repository.dto.response.TourPackageFileResponse;
import com.healthtrip.travelcare.repository.tour.TourPackageFileRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TourPackageFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourPackageFileService {
    private final TourPackageRepository tourPackageRepository;
    private final TourPackageFileRepository tourPackageFileRepository;

    private final AwsS3Service awsS3Service;
    private static final String IMAGE_DIRECTORY = "images/tour/package"; // 패키지 이미지폴더로 이동예정

    @Transactional
    public TourPackageFile uploadTourPackageImage(MultipartFile file,TourPackage tourPackage) {
        String fileName = CommonUtils.buildFileName(Objects.requireNonNull(file.getOriginalFilename()),IMAGE_DIRECTORY);
        String url = awsS3Service.uploadS3File(file,fileName);
        var tourPackageFile = TourPackageFile.builder()
                .url(url)
                .fileSize(file.getSize())
                .fileName(fileName)
                .originalName(file.getOriginalFilename())
                .tourPackage(tourPackage)
                .build();
        return tourPackageFileRepository.save(tourPackageFile);
    }

    @Transactional
    public List<TourPackageFile> uploadTourPackageMultipleImage(List<MultipartFile> files, TourPackage tourPackage){
        List<TourPackageFile> tourPackageFiles = new ArrayList<>();

        for (MultipartFile file:files){
        String fileName = CommonUtils.buildFileName(Objects.requireNonNull(file.getOriginalFilename()), IMAGE_DIRECTORY);
        String url = awsS3Service.uploadS3File(file,fileName);

        tourPackageFiles.add(TourPackageFile.builder()
                .url(url)
                .fileSize(file.getSize())
                .fileName(fileName)
                .originalName(file.getOriginalFilename())
                .tourPackage(tourPackage)
                .build()
                );
        }
        //saveAll
        return tourPackageFileRepository.saveAll(tourPackageFiles);

    }

    @Transactional(readOnly = true)
    public List<TourPackageFileResponse> getImages(Long tripPackageId) {
        var files = tourPackageFileRepository.findByTourPackageId(tripPackageId);
        return files.stream().map(TourPackageFileResponse::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void addImage(Long tourPackageId, TourPackageFileRequest request) {
        TourPackage tourPackage = tourPackageRepository.getById(tourPackageId);

        // 파일 널체크
        List<MultipartFile> files = request.getFiles();
        for (MultipartFile file:files){
            boolean nullCheck = file.getSize() == 0;
            boolean equalsCheck =file.getOriginalFilename().equals("");

            if (nullCheck || equalsCheck){
                System.out.println("you send void file");
                throw new RuntimeException("you send void file");
            };
        }
        // 2. 이미지 생성
        try {
            uploadTourPackageMultipleImage(files, tourPackage);
        }catch (Exception e){
            System.out.println("생성오류"+e); // 이거 로그로 처리
            throw new RuntimeException("업로드 생성오류");
        }

    }

    @Transactional
    public void deleteImage(List<Long> ids) {
        List<TourPackageFile> files = tourPackageFileRepository.findAllById(ids);
        if (files.isEmpty()){
            return;
        }
        var nameList = files.stream().map(TourPackageFile::getFileName);
        boolean result = awsS3Service.deleteFileByNameList(nameList);
        if(result){
            tourPackageFileRepository.deleteAllById(ids);
        }
    }
}