package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.TripPackage;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.TpResponseDto;
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
    public ResponseEntity allTripPack() {
        // 레포지에서 정보 꺼내오기
        // 제목,가격 담기
        // 이미지 담기
        // 전달
        List<TpResponseDto> tpResponseDtoList = new ArrayList<>();

        List<TripPackage> tripPackageList = tpRepository.findAll();

        tripPackageList.forEach((entity) -> {
            TpResponseDto tpResponseDto = TpResponseDto.entityToDto(entity);

//            tpResponseDto.setTripPackageFileResponseDto();
            // entity -> dto -> dtoList
//            tpResponseDtoList.add();
        });
        return ResponseEntity.ok().body(tpResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity oneTripPack(Long id) {
        // 패키지 정보
        // 패키지 이미지 정보
        // 패키지 여행 가능날짜 정보, 현인원/최대인원

        Optional<TripPackage> tripPackage = tpRepository.findById(17L);

        ResponseEntity responseEntity;

        if (tripPackage.isPresent()){
            return responseEntity = tripPackage.map(tpPackageEntity ->
                            ResponseEntity
                                    .ok()
                                    .body(TpResponseDto.entityToDto(tpPackageEntity)))
                                    .get();
        }else{
            return responseEntity = ResponseEntity.status(200).body("패키지를 찾을 수 없음");
        }

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
