package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageTendency;
import com.healthtrip.travelcare.repository.TendencyRepository;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.response.TourPackageFileResponse;
import com.healthtrip.travelcare.repository.tour.TourItineraryElementRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TourPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TourPackageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    private final TourPackageFileService tourPackageFileService;

    private final AccountsRepository accountsRepository;
    private final TourItineraryElementRepository itineraryElementRepository;
    private final TendencyRepository tendencyRepository;


    @Transactional(readOnly = true)
    public List<TourPackageResponse.TPBasicInfo> mainPagePackages(Long tendencyId) {
        // dto 초기화
        List<TourPackageResponse.TPBasicInfo> basicPackageInfoDTOS = new ArrayList<>();

        // 레포지에서 정보 꺼내오기
        List<TourPackage> tourPackageList;
        if (tendencyId !=null){
            tourPackageList = tourPackageRepository.searchWithUserTendency(tendencyId);
            if (tourPackageList.isEmpty()) {
                tourPackageList = tourPackageRepository.mainPageTourPackage();
            }
        }else {
            tourPackageList = tourPackageRepository.mainPageTourPackage();
        }

        // 정보 dto에 담기
        tourPackageList.forEach(tourPackage -> {
            basicPackageInfoDTOS.add(TourPackageResponse.TPBasicInfo.toMainPageDTO(tourPackage));
        });

        return basicPackageInfoDTOS;
    }

    @Transactional(readOnly = true)
    public TourPackageResponse.tourWithImages tourPackageDetails(Long id) {
        var response = new TourPackageResponse.tourWithImages();
        // 패키지 찾기
        var tourPackage = tourPackageRepository.getTourPackageAndImages(id);
        response.setTourPackageResponse(TourPackageResponse.toResponse(tourPackage));

        // 이미지
        response.setMainImage(TourPackageFileResponse.toResponse(tourPackage.getMainImage()));
        response.setTourImages(
            tourPackage.getTourPackageFileList().stream().map(TourPackageFileResponse::toResponse).collect(Collectors.toList())
        );

        // 패키지
        return response;
    }


    @Transactional
    public ResponseEntity addTourPack(TourPackageRequestDto tourPackageRequestDto) {
        // 1. 패키지 생성
        Account account = accountsRepository.getById(CommonUtils.getAuthenticatedUserId());
        TourPackage tourPackage = tourPackageRequestDto.toEntity(tourPackageRequestDto);

        // 계정
        tourPackage.setAccount(account);
        // 썸네일
        var mainImage = tourPackageRequestDto.getMainImage();
        tourPackage.setMainImage(tourPackageFileService.uploadTourPackageImage(mainImage));

        // 패키지 이미지 추가
        var packageImages = tourPackageRequestDto.getPackageImages();
        if (packageImages != null){
            var savedTourPackageFileList = tourPackageFileService.uploadTourPackageMultipleImage(packageImages,tourPackage);
            tourPackage.setTourPackageFileList(savedTourPackageFileList);
        }

        // 패키지 성향 추가
        var tendencyId= tourPackageRequestDto.getTendencyId();
        if (tendencyId != null) {
            var tendency = tendencyRepository.getById(tendencyId);
            var tourPackageTendency = TourPackageTendency.builder()
                    .tourPackage(tourPackage)
                    .tendency(tendency)
                    .build();
            tourPackage.addTendency(tourPackageTendency);
        }

        // 패키지 생성 완료
        TourPackage savedTourPackage = tourPackageRepository.save(tourPackage);

        return ResponseEntity.ok("생성완료");
    }

    @Transactional
    public void addTendency(Long id, Long tendencyId) {
        var tourPackage= tourPackageRepository.getById(id);
        var tendency=tendencyRepository.getById(tendencyId);
        var tourPackageTendency = TourPackageTendency.builder()
                .tourPackage(tourPackage)
                .tendency(tendency)
                .build();
        tourPackage.addTendency(tourPackageTendency);
    }


}
