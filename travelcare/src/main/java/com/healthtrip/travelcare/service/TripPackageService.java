package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.TripPackage;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.ReservationDateRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.ReservationDateResponse;
import com.healthtrip.travelcare.repository.dto.response.TripPackageFileResponse;
import com.healthtrip.travelcare.repository.dto.response.TripPackageResponse;
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

    private final ReservationDateRepository reservationDateRepository;

    @Transactional(readOnly = true)
    public List<TripPackageResponse.MainPagePack> allTripPack() {
        // 초기화
        List<TripPackageResponse.MainPagePack> mainPagePackPackages = new ArrayList<>();

        // 레포지에서 정보 꺼내오기
        List<TripPackage> tripPackageList = tpRepository.findAll();

        // 정보 dto에 담기
        tripPackageList.forEach(tripPackage -> {
            // 썸네일 이미지 객체 받기
            var tripPackageFile = tripPackage.getTripPackageFileList().get(0);

            // 썸네일 dto로 변환
            var tpfr = TripPackageFileResponse.MainPagePackImage.builder()
                    .id(tripPackageFile.getId())
                    .url(tripPackageFile.getUrl())
                    .build();

            // id 제목 가격 담기
            var mtpr= TripPackageResponse.MainPagePack.builder()
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
    public TripPackageResponse.TripPackInfo oneTripPack(Long id) {
        TripPackageResponse.TripPackInfo responseDto = null;

        // 모든 데이터 유무 판별
        Optional<TripPackage> optionalTripPackage = tpRepository.findById(id);
        boolean present = optionalTripPackage.isPresent();

        // 존재
        if(present){
            TripPackage tripPackage =optionalTripPackage.get();

            // 연관관계 데이터 찾아오기
            var files = tripPackage.getTripPackageFileList();
            var dates = tripPackage.getReservationDateList();

            // 널체크
            boolean fileEmpty = files.isEmpty();
            boolean dateEmpty = dates.isEmpty();

            if (fileEmpty) {
                System.out.println((">>>tripPackage ID: "+tripPackage.getId()+"'s File Empty"));
            }
            if (dateEmpty) {
                System.out.println((">>>tripPackage ID: "+tripPackage.getId()+"'s Data Empty"));
            }

            // 파일 있는 경우
            if (!(fileEmpty && dateEmpty)){

                // 응답 객체에 담을 dto들 선언
                var imageDtoList = new ArrayList<TripPackageFileResponse.FileInfo>();
                var dateDtoList = new ArrayList<ReservationDateResponse.DateInfoAll>();

                // entity to dto List
                files.forEach(tripPackageFile -> {
                    imageDtoList.add(
                            TripPackageFileResponse.FileInfo.builder()
                                    .id(tripPackageFile.getId())
                                    .name(tripPackageFile.getFileName())
                                    .url(tripPackageFile.getUrl())
                                    .build()
                    );
                });
                dates.forEach(reservationDate -> {
                    dateDtoList.add(
                            ReservationDateResponse.DateInfoAll.builder()
                                    .id(reservationDate.getId())
                                    .departAt(reservationDate.getDepartAt())
                                    .arriveAt(reservationDate.getArriveAt())
                                    .currentNumPeople(reservationDate.getCurrentNumPeople())
                                    .peopleLimit(reservationDate.getPeopleLimit())
                                    .build()
                    );
                });

                // 응답 객체에 저장
                responseDto = TripPackageResponse.TripPackInfo.builder()
                        // trip package
                        .packageId(tripPackage.getId())
                        .description(tripPackage.getDescription())
                        .price(tripPackage.getPrice())
                        .title(tripPackage.getTitle())
                        .type(tripPackage.getType())

                        // file
                        .images(imageDtoList)

                        // date
                        .dates(dateDtoList)
                        .build();
            }
        }else{
            // tripPackage 없음
        }
        return responseDto;
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
