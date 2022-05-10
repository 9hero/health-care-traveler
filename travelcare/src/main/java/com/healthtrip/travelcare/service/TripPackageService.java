package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.TripPackage;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TripPackageFileResponseDto;
import com.healthtrip.travelcare.repository.dto.response.TripPackageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripPackageService {

    private final TripPackageRepository tpRepository;

    private final AccountsRepository accountsRepository;

    @Transactional(readOnly = true)
    public List<TripPackageResponseDto.mainPagePack> allTripPack() {
        // 초기화
        List<TripPackageResponseDto.mainPagePack> mainPagePackPackages = new ArrayList<>();

        // 레포지에서 정보 꺼내오기
        List<TripPackage> tripPackageList = tpRepository.findAll();

        // 정보 dto에 담기
        tripPackageList.forEach(tripPackage -> {
            // 썸네일 이미지 객체 받기
            var tripPackageFile = tripPackage.getTripPackageFileList().get(0);

            // 썸네일 dto로 변환
            var tpfr = TripPackageFileResponseDto.mainPagePackImage.builder()
                    .id(tripPackageFile.getId())
                    .url(tripPackageFile.getUrl())
                    .build();

            // id 제목 가격 담기
            var mtpr= TripPackageResponseDto.mainPagePack.builder()
                    .id(tripPackage.getId())
                    .title(tripPackage.getTitle())
                    .price(tripPackage.getPrice())
                    .build();

            // 이미지 담기
            mtpr.setThumbnail(tpfr);

            mainPagePackPackages.add(mtpr);
        });

        return mainPagePackPackages;
    }

    @Transactional(readOnly = true)
    public ResponseEntity oneTripPack(Long id) {

        return ResponseEntity.status(200).body("패키지를 찾을 수 없음");
    }


    @Transactional
    public ResponseEntity addTripPack(TripPackageRequestDto tripPackageRequestDto) {

        // 1. 패키지 생성
        Optional<Account> account = accountsRepository.findById(tripPackageRequestDto.getAccountId());
        if(account.isPresent()) {
            TripPackage tripPackage = tripPackageRequestDto.toEntity(tripPackageRequestDto);
            tripPackage.setAccount(account.get());
            TripPackage savedTripPackage = tpRepository.save(tripPackage);
            return ResponseEntity.created(URI.create("/")).build();
        }else{
            return ResponseEntity.status(401).body("패키지를 등록하려는 계정을 찾을 수 없습니다. " +
                    "관리자에게 문의 해주세요.");
        }


        // 2. 이미지 생성

        // 3. 연관관계 설정


    }
}
