package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.domain.entity.travel.trip_package.TripPackage;
import com.healthtrip.travelcare.domain.entity.travel.trip_package.TripPackageFile;
import com.healthtrip.travelcare.repository.TripPackageFileRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
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
public class TripPackageFileService {
    private final TripPackageRepository tripPackageRepository;
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

    @Transactional(readOnly = true)
    public List<TripPackageFileResponse.FileInfo> getData(Long tripPackageId) {
        var files = tripPackageFileRepository.findByTripPackageId(tripPackageId);

        boolean fileEmpty = files.isEmpty();
        if (fileEmpty) {
            System.out.println((">>>tripPackage ID: "+tripPackageId+"'s File Empty"));
            throw new RuntimeException();
        }
        var imageDtoList = new ArrayList<TripPackageFileResponse.FileInfo>();
        files.forEach(tripPackageFile -> {
            imageDtoList.add(
                    TripPackageFileResponse.FileInfo.builder()
                            .id(tripPackageFile.getId())
                            .name(tripPackageFile.getFileName())
                            .url(tripPackageFile.getUrl())
                            .build()
            );
        });
        return imageDtoList;
    }

    @Transactional
    public void addImage(TripPackageFileRequest request) {
        TripPackage tripPackage = tripPackageRepository.getById(request.getTripPackageId());

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
            uploadTripImage(files,tripPackage);
        }catch (Exception e){
            System.out.println("생성오류"+e); // 이거 로그로 처리
            throw new RuntimeException("업로드 생성오류");
        }

    }

    @Transactional
    public void deleteImage(List<Long> ids) {
        List<TripPackageFile> files = tripPackageFileRepository.findAllById(ids);
        var nameList = files.stream().map(TripPackageFile::getFileName);
        boolean result = awsS3Service.deleteFileByNameList(nameList);
        if(result){
        tripPackageFileRepository.deleteAllById(ids);
            System.out.println("성공");
        }
    }
}