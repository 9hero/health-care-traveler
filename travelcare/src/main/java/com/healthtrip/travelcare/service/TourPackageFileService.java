package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import com.healthtrip.travelcare.repository.TourPackageFileRepository;
import com.healthtrip.travelcare.repository.TourPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TripPackageFileRequest;
import com.healthtrip.travelcare.repository.dto.response.TripPackageFileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TourPackageFileService {
    private final TourPackageRepository tourPackageRepository;
    private final TourPackageFileRepository tourPackageFileRepository;

    private final AwsS3Service awsS3Service;
    private static final String IMAGE_DIRECTORY = "images/trip-package"; // 패키지 이미지폴더로 이동예정

    @Transactional
    public void uploadTripImage(List<MultipartFile> files, TourPackage tourPackage) throws Exception {
        List<TourPackageFile> tourPackageFiles = new ArrayList<>();

        // 이렇게되면 패키지 등록 실패해도 파일이 남아있음 실패하면
        // 이유 : 파일 먼저 생성,패키지 등록하기 때문
        // 패키지 먼저등록 -> 업데이트 =  기존: 파일생성 + 엔티티저장 = 2 연결
        // 앤티티저장 -> 파일생성 -> url 업데이트 = 3 연결
        // 패키지 등록 실패확률 vs 3연결
        // 고로 파일 널체크하고 들어감
        for (MultipartFile file:files){
            String fileName = CommonUtils.buildFileName(Objects.requireNonNull(file.getOriginalFilename()), IMAGE_DIRECTORY);
            String url = awsS3Service.uploadFileV1(file,fileName);

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
        tourPackageFileRepository.saveAll(tourPackageFiles);
        // updateAll
    }

    @Transactional(readOnly = true)
    public List<TripPackageFileResponse.FileInfo> getData(Long tripPackageId) {
        var files = tourPackageFileRepository.findByTourPackageId(tripPackageId);

        boolean fileEmpty = files.isEmpty();
        if (fileEmpty) {
            System.out.println((">>>tripPackage ID: "+tripPackageId+"'s File Empty"));
            throw new RuntimeException();
        }
        var imageDtoList = new ArrayList<TripPackageFileResponse.FileInfo>();
        files.forEach(tourPackageFile -> {
            imageDtoList.add(
                    TripPackageFileResponse.FileInfo.builder()
                            .id(tourPackageFile.getId())
                            .name(tourPackageFile.getFileName())
                            .url(tourPackageFile.getUrl())
                            .build()
            );
        });
        return imageDtoList;
    }

    @Transactional
    public void addImage(TripPackageFileRequest request) {
        TourPackage tourPackage = tourPackageRepository.getById(request.getTripPackageId());

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
            uploadTripImage(files, tourPackage);
        }catch (Exception e){
            System.out.println("생성오류"+e); // 이거 로그로 처리
            throw new RuntimeException("업로드 생성오류");
        }

    }

    @Transactional
    public void deleteImage(List<Long> ids) {
        List<TourPackageFile> files = tourPackageFileRepository.findAllById(ids);
        var nameList = files.stream().map(TourPackageFile::getFileName);
        boolean result = awsS3Service.deleteFileByNameList(nameList);
        if(result){
        tourPackageFileRepository.deleteAllById(ids);
            System.out.println("성공");
        }
    }
}