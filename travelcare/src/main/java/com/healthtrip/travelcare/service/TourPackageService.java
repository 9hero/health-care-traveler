package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TourPackageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    private final TourPackageFileService tourPackageFileService;

    private final AccountsRepository accountsRepository;

    private final TourPackageDateRepository tourPackageDateRepository;


    @Transactional(readOnly = true)
    public List<TourPackageResponse.TPBasicInfo> mainPagePackages() {
        // dto 초기화
        List<TourPackageResponse.TPBasicInfo> basicPackageInfoDTOS = new ArrayList<>();

        // 레포지에서 정보 꺼내오기
        List<TourPackage> tourPackageList = tourPackageRepository.findAll();

        // 정보 dto에 담기
        tourPackageList.forEach(tourPackage -> {
            basicPackageInfoDTOS.add(TourPackageResponse.TPBasicInfo.toMainPageDTO(tourPackage));
        });

        return basicPackageInfoDTOS;
    }

    @Transactional(readOnly = true)
    public TourPackageResponse.TPBasicInfo tourPackageInfo(Long id) {
        // 패키지 찾기
        var tourPackage = tourPackageRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("tp not found", HttpStatus.BAD_REQUEST);
        });
        // 패키지
        return TourPackageResponse.TPBasicInfo.toMainPageDTO(tourPackage);
        // 이미지는 프론트에서 API로 불러옴
    }


    @Transactional
    public ResponseEntity addTripPack(TripPackageRequestDto tripPackageRequestDto) {
        System.out.println(tripPackageRequestDto);
        // 1. 패키지 생성
        Optional<Account> account = accountsRepository.findById(tripPackageRequestDto.getAccountId());
        if(account.isPresent()) {

            TourPackage tourPackage = tripPackageRequestDto.toEntity(tripPackageRequestDto);
            tourPackage.setAccount(account.get());
            TourPackage savedTourPackage = tourPackageRepository.save(tourPackage);

        // 파일 널체크
            List<MultipartFile> files = tripPackageRequestDto.getMultipartFiles();
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
            tourPackageFileService.uploadTripImage(files, savedTourPackage);
            }catch (Exception e){
                System.out.println("생성오류"+e); // 이거 로그로 처리
                throw new RuntimeException("업로드 생성오류");
            }
            return ResponseEntity.ok("생성완료");
        }else{
            return ResponseEntity.status(401).body("패키지를 등록하려는 계정을 찾을 수 없습니다. " +
                    "관리자에게 문의 해주세요.");
        }


        // 3. 연관관계 설정(여행일자도 한번에 등록시)


    }


}
