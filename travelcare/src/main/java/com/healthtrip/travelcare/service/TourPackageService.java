package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.TripPackageRequestDto;
import com.healthtrip.travelcare.repository.dto.response.ReservationDateResponse;
import com.healthtrip.travelcare.repository.dto.response.TripPackageFileResponse;
import com.healthtrip.travelcare.repository.dto.response.TripPackageResponse;
import lombok.RequiredArgsConstructor;
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

    private final TourPackageRepository tpRepository;

    private final TourPackageFileService tourPackageFileService;

    private final AccountsRepository accountsRepository;

    private final TourPackageDateRepository tourPackageDateRepository;


    @Transactional(readOnly = true)
    public List<TripPackageResponse.MainPagePack> allTripPack() {
        // 초기화
        List<TripPackageResponse.MainPagePack> mainPagePackPackages = new ArrayList<>();

        // 레포지에서 정보 꺼내오기
        List<TourPackage> tourPackageList = tpRepository.findAll();

        // 정보 dto에 담기
        tourPackageList.forEach(tripPackage -> {

            // 썸네일 이미지 객체 받기
            var files = tripPackage.getTourPackageFileList();
            if(files.isEmpty()){
                return;
            }
            // id 제목 가격 담기
            var mtpr= TripPackageResponse.MainPagePack.builder()
                    .id(tripPackage.getId())
                    .title(tripPackage.getTitle())
                    .price(tripPackage.getPrice())
                    .build();
            var tripPackageFile = tripPackage.getTourPackageFileList().get(0);

            // 썸네일 dto로 변환
            var tpfr = TripPackageFileResponse.MainPagePackImage.builder()
                    .id(tripPackageFile.getId())
                    .url(tripPackageFile.getUrl())
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
        Optional<TourPackage> optionalTripPackage = tpRepository.findById(id);
        boolean present = optionalTripPackage.isPresent();

        // 존재
        if(present){
            TourPackage tourPackage =optionalTripPackage.get();

            // 연관관계 데이터 찾아오기
            var files = tourPackage.getTourPackageFileList();
            var dates = tourPackage.getTourPackageDateList();

            // 널체크
            boolean fileEmpty = files.isEmpty();
            boolean dateEmpty = dates.isEmpty();

            if (fileEmpty) {
                System.out.println((">>>tripPackage ID: "+ tourPackage.getId()+"'s File Empty"));
            }
            if (dateEmpty) {
                System.out.println((">>>tripPackage ID: "+ tourPackage.getId()+"'s Data Empty"));
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
                                    .name(tripPackageFile.getOriginalName())
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
                        .packageId(tourPackage.getId())
                        .description(tourPackage.getDescription())
                        .price(tourPackage.getPrice())
                        .title(tourPackage.getTitle())
                        .type(tourPackage.getType())

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
        System.out.println(tripPackageRequestDto);
        // 1. 패키지 생성
        Optional<Account> account = accountsRepository.findById(tripPackageRequestDto.getAccountId());
        if(account.isPresent()) {

            TourPackage tourPackage = tripPackageRequestDto.toEntity(tripPackageRequestDto);
            tourPackage.setAccount(account.get());
            TourPackage savedTourPackage = tpRepository.save(tourPackage);

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
