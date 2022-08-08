package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.location.Address;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservationPerson;
import com.healthtrip.travelcare.repository.*;
import com.healthtrip.travelcare.repository.dto.request.AddressRequest;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationInfoService {

    // 코드 줄이려면 ddd or 다른 서비스와 통합 후 불러오기
    private final AccountsRepository accountsRepository;
    private final AccountCommonRepository commonRepository;
    private final TourReservationRepository tourReservationRepository;
    private final TourPackageDateRepository tourPackageDateRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final TourReservationPersonRepository tourReservationPersonRepository;

    @Transactional
    public ResponseEntity<String> reserveTripPackage(ReservationRequest.ReserveData reserveData) {

        // DTO -> local 변수
        Long uid = CommonUtils.getAuthenticatedUserId();
        var addressList = reserveData.getAddressData();
        var personDataList = reserveData.getReservationPersonData();
        short personCount = (short) personDataList.size(); // 대표자 주소일 경우 주소데이터 1임

        // 분기값
        boolean agentUser = reserveData.getRole().equals(Account.UserRole.ROLE_AGENT);
        boolean singleType = reserveData.getAddressType().equals(ReservationRequest.AddressType.SINGLE);

        // 예약인원 Exception: 나중에 validation 으로 검증할 예정
        // 일반 유저가 인적사항 데이터가 없고 forEach 일 경우 그리고 Agent가 데이터를 누락시킨 경우
        if ( (!agentUser && personCount == 0 && !singleType) || (agentUser && personCount ==0) ) {
            return ResponseEntity.badRequest().body("Error: Can not reserve tour");
        }

        // 연관관계 설정을 위한 id 받아오기 t-1
        var optional = tourPackageDateRepository.findById(reserveData.getDateId());
        if (optional.isPresent()) {
            TourPackageDate tourPackageDate = optional.get();
            Account account = accountsRepository.getById(uid);

            // 예약 금액 계산 amount = (예약인원*투어가격) t-2
            BigDecimal amount = BigDecimal.valueOf(personDataList.size())
                    .multiply(tourPackageDate.getTourPackage().getPrice());

            // 인원체크
            boolean limitOver = tourPackageDate.plusCurrentPeopleNumber(personCount);
            if (limitOver) return ResponseEntity.badRequest().body("Error: Limit Over");

            // 예약 하기
            TourReservation tourReservation = TourReservation.builder()
//                    .id()
                    .account(account)
                    .tourPackageDate(tourPackageDate)
                    .personCount(personCount)
                    .status(TourReservation.Status.Y)
                    .csStatus(TourReservation.CsStatus.K)
                    .paymentStatus(TourReservation.PaymentStatus.N)
                    .amount(amount)
                    .build();
            // 주문번호 생성 후 저장 중복번호일 경우 4번까지만 시도함 실패시
            TourReservation savedTourReservation = saveReservationInfo(tourReservation);
            if (savedTourReservation == null)return ResponseEntity.ok("Error: 예약실패. 관리자에게 문의하세요.");

            if (singleType) {

                // ----Address start-----
                Address singleAddress = null;

                // 주소 객체 생성 common agent 분기
                if(agentUser){
                    // 입력값 등록 -> 대표자 주소값(0번지)
                    AddressRequest representative = addressList.get(0);
                    singleAddress = Address.toEntityBasic(representative);
                    Country country = countryRepository.getById(representative.getCountryId());
                    singleAddress.setCountry(country);
                }else {
                    // 일반유저의 경우, 대표자 주소는 계정의 주소 값으로 등록 t-3
                    singleAddress = commonRepository.getById(uid).getAddress();
                }
                // t-3
                Address savedAddress = addressRepository.save(singleAddress);
                // ----Address done-----

                // 인적사항 Dto -> Entity로 변환 -> persist
                var reservationPersonList = personDataList
                        .stream().map(personData->{
                             var personEntity = TourReservationPerson.reservationPersonBasicEntity(personData);
                             // address,info 연관관계설정
                             personEntity.relationSet(savedTourReservation, savedAddress);
                             return personEntity;
                        }).collect(Collectors.toList());
                // t-4 saveAll sql 생성문 확인바람(n+1)
                System.out.println(">>> is n+1? ");
                tourReservationPersonRepository.saveAll(reservationPersonList);

            }else { // 주소 각 입력일 경우,
                // 주소입력수 예약인원입력수 같지 않으면 오류
                if (addressList.size() != personDataList.size()) return ResponseEntity.badRequest().body("Error: 입력한 주소의 갯수와 고객의 수가 같지않습니다.");
                // 주소입력수 만큼 Entity 생성
                List<TourReservationPerson> tourReservationPersonList = new ArrayList<>();
                for(int i = 0; i<personDataList.size(); i++){
                    AddressRequest addressRequest = addressList.get(i);
                    Country country = countryRepository.getById(addressRequest.getCountryId());
                    Address address = Address.toEntityBasic(addressRequest);
                    address.setCountry(country);
                    TourReservationPerson tourReservationPerson = TourReservationPerson.reservationPersonBasicEntity(personDataList.get(i));
                    tourReservationPerson.relationSet(savedTourReservation,address);
                    // add
                    tourReservationPersonList.add(tourReservationPerson);
                }
                // saveAll
                tourReservationPersonRepository.saveAll(tourReservationPersonList);
            }
        }else {
            return ResponseEntity.badRequest().body("Error: 해당 여행일짜 없음");
        }
        return ResponseEntity.ok("OK: 예약 등록 완료");
    }

    public void tourReservation() {
        // dto to local variable
        // save ReservationInfo & Address & Person
    }

    private TourReservation saveReservationInfo(TourReservation tourReservation) {
        for (int i = 0; i<4;i++){
            boolean conflict = tourReservationRepository.existsById(tourReservation.idGenerate());
            if (!conflict) {
                return tourReservationRepository.save(tourReservation);
            }else {
                if(i == 3){
                    log.info("예약번호 4연속 중복, 오늘자 :{}", LocalDateTime.now());
                    return null;
                }
            }
        }
        return null;
    }

    @Transactional
    public ResponseEntity cancelReservation(String reservationId) {
        tourReservationRepository.deleteById(reservationId);
        return ResponseEntity.ok("별 다른 로직 없이 삭제만 했어요");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationInfoResponse.MyInfo>> myReservation() {
//        필요정보 : 예약번호, 예약자, 예약 인원, 패키지명, 가격, 출발일, 도착일, 예약상태
//        필요 Entity: paged info,Date,TripPackage,ReservationPerson
        Long uid = CommonUtils.getAuthenticatedUserId();
        // 내 예약정보 전부 가져오기
        List<TourReservation> info = tourReservationRepository.findByAccountId(uid);
        if(!info.isEmpty()){
            // 예약정보 있는경우: 응답값 초기화 및 필요한 객체들 불러오기
            List<ReservationInfoResponse.MyInfo> responseBody = new ArrayList<>();
            info.forEach(reservationInfo -> {
                ReservationInfoResponse.MyInfo myInfo = new ReservationInfoResponse.MyInfo();
                var reservationDate = reservationInfo.getTourPackageDate();
                var reservedPerson = reservationInfo.getTourReservationPeople();
                var tripPackage = reservationDate.getTourPackage();
                var representPerson = reservedPerson.get(0);

                // 예약 번호
                myInfo.setReservationInfoId(reservationInfo.getId());
                // 예약자(임시로 대표자 이름만)
                myInfo.setPersonName(representPerson.getFirstName() + " "+representPerson.getLastName());
                // 예약상태, 에약인원
                myInfo.setStatus(reservationInfo.getStatus());
                myInfo.setPersonCount(reservationInfo.getPersonCount());

                // 패키지명, 가격
                myInfo.setPackageTitle(tripPackage.getTitle());
                myInfo.setPrice(tripPackage.getPrice());

                // 출발일 도착일
                myInfo.setDepartAt(reservationDate.getDepartAt());
                myInfo.setArrivedAt(reservationDate.getArriveAt());

                responseBody.add(myInfo);
            });

            return ResponseEntity.ok(responseBody);
        }else{
            //내 예약 정보없음
            return ResponseEntity.ok().body(null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationPersonResponse.rpInfo>> getPeopleDataByInfoId(Long reservationId) {
        Long uid = CommonUtils.getAuthenticatedUserId();
        List<TourReservationPerson> reservationPeople = tourReservationPersonRepository.findByTourReservationId(reservationId,uid);
        var responseBody = reservationPeople.stream().map(person ->
                ReservationPersonResponse.rpInfo.builder()
                        .reservedPersonId(person.getId())
                        .addressId(person.getAddress().getId())
                        .birth(person.getBirth())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .gender(person.getGender())
                        .emergencyContact(person.getEmergencyContact())
                        .phone(person.getPhone())
                        .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(responseBody);
    }
}
