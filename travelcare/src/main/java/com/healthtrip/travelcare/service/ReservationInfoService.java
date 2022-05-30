package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.*;
import com.healthtrip.travelcare.repository.*;
import com.healthtrip.travelcare.repository.dto.request.AddressRequest;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationInfoService {

    // 코드 줄이려면 ddd or 다른 서비스와 통합 후 불러오기
    private final AccountsRepository accountsRepository;
    private final AccountCommonRepository commonRepository;
    private final ReservationInfoRepository reservationInfoRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final ReservationPersonRepository reservationPersonRepository;

    @Transactional
    public ResponseEntity reservePackage(ReservationRequest.ReserveData reserveData) {
        // 유저 분기
        Long uid = reserveData.getUserId();
        boolean agentUser = reserveData.getRole().equals(Account.UserRole.ROLE_AGENT);

        // 연관관계 설정을 위한 id 받아오기
        var optional = reservationDateRepository.findById(reserveData.getDateId());
        if(optional.isPresent()){
            ReservationDate reservationDate = optional.get();
            boolean limitOver = reservationDate.plusCurrentPeopleNumber(reserveData.getPersonCount());
            if (limitOver) return ResponseEntity.ok("limit Over");
        // 널체크 필요
        Account account = accountsRepository.getById(uid);

        // 예약 하기
        ReservationInfo reservationInfo = ReservationInfo.builder()
                .reservationDate(reservationDate)
                .account(account)
                .personCount(reserveData.getPersonCount())
                .status(ReservationInfo.Status.Y)
                .build();
        var savedReservationInfo = reservationInfoRepository.save(reservationInfo);

        // 주소 등록
        Address savedAddress = null;
        if(agentUser) {
            var addressData = reserveData.getAddressData().get(0);
            Address address = Address.builder()
                    .address(addressData.getAddress1())
                    .addressDetail(addressData.getAddress2())
                    .district(addressData.getDistrict())
                    .city(addressData.getCityName())
                    .postalCode(addressData.getPostalCode())
                    .country(countryRepository.getById(addressData.getCountryId()))
                    .build();

            savedAddress = addressRepository.save(address);
        }else{
            // 주소불러오기 client에서 address id 안보내줄 경우
            savedAddress = commonRepository.getById(uid).getAddress();

            // 보내줄시 ( client에서 따로 '내 주소 불러오기' API 사용해서 id 받아온후 이쪽으로 토스)
            // saveAddress = accountsRepository.getById(reserveData.getAddressId())
        }

        //예약자 등록
        var peopleDataList = reserveData.getReservationPersonData();

        // 람다식에서 사용할 final Entity
        Address finalSavedAddress = savedAddress;

        // saveAll을 위한 entity 모음 초기화
        List<ReservationPerson> reservationPersonList = new ArrayList<>();

        // 하나씩 생성후 모음에 넣어줌
        peopleDataList.forEach(peopleData -> {
        var reservationPerson = ReservationPerson.builder()
                .reservationInfo(savedReservationInfo)
                .firstName(peopleData.getFirstName())
                .lastName(peopleData.getLastName())
                .gender(peopleData.getGender())
                .birth(peopleData.getBirth())
                .phone(peopleData.getPhone())
                .emergencyContact(peopleData.getEmergencyContact())
                .address(finalSavedAddress)
                .build();
            reservationPersonList.add(reservationPerson);
        });

        reservationPersonRepository.saveAll(reservationPersonList);

        }else {
            return ResponseEntity.badRequest().body("해당 여행일짜 없음");
        }
        return ResponseEntity.ok("예약 등록 완료");
    }



    @Transactional
    public ResponseEntity<String> temp(ReservationRequest.ReserveData reserveData) {

        // 분기,데이터 꺼내기 개별로 국적 다르게 들어가서 좀 낭비가 생김
        Long uid = reserveData.getUserId();
        boolean agentUser = reserveData.getRole().equals(Account.UserRole.ROLE_AGENT);
        boolean singleType = reserveData.getAddressType().equals(ReservationRequest.AddressType.SINGLE);
        var addressList = reserveData.getAddressData();
        var personDataList = reserveData.getReservationPersonData();
        short personCount = reserveData.getPersonCount();

        // 연관관계 설정을 위한 id 받아오기
        var optional = reservationDateRepository.findById(reserveData.getDateId());
        if (optional.isPresent()) {
            ReservationDate reservationDate = optional.get();
            boolean limitOver = reservationDate.plusCurrentPeopleNumber(personCount);
            if (limitOver) return ResponseEntity.ok("limit Over");
            // 널체크 필요 1. 키가 null, 2. 엔티티를 찾지못함
            Account account = accountsRepository.getById(uid);

            // 예약 하기
            ReservationInfo reservationInfo = ReservationInfo.builder()
                    .reservationDate(reservationDate)
                    .account(account)
                    .personCount(personCount)
                    .status(ReservationInfo.Status.Y)
                    .build();
            var savedReservationInfo = reservationInfoRepository.save(reservationInfo);

            if (singleType) {
                // ----Address start-----
            Address singleAddress = null;
                // 주소 객체 생성 common agent 분기
                if(agentUser){
                    // 입력값 등록 -> 대표자 주소값
                    AddressRequest representative = addressList.get(0);
                    singleAddress = Address.toEntityBasic(representative);
                    Country country = countryRepository.getById(representative.getCountryId());
                    singleAddress.setCountry(country);
                }else {
                    // 계정의 주소 값으로 등록
                    singleAddress = commonRepository.getById(uid).getAddress();
                }

                Address savedAddress = addressRepository.save(singleAddress);
                // ----Address done-----

                // dto to entity
                var reservationPersonList = personDataList
                        .stream().map(personData->{
                             var personEntity = ReservationPerson.reservationPersonBasicEntity(personData);
                             // address,info 연관관계설정
                             personEntity.relationSet(savedReservationInfo, savedAddress);
                             return personEntity;
                        }).collect(Collectors.toList());

                // done
                reservationPersonRepository.saveAll(reservationPersonList);
            }else { // list -> one
                // 주소입력수 예약인원입력수 비교 같아야지 ㄱ 아니면 오류
                if (addressList.size() != personDataList.size()) return ResponseEntity.ok("입력한 주소의 갯수와 고객의 수가 같지않습니다.");
                List<ReservationPerson> reservationPersonList = new ArrayList<>();
                for(int i = 0; i<personDataList.size(); i++){
                    AddressRequest addressRequest=  addressList.get(i);
                    Country country = countryRepository.getById(addressRequest.getCountryId());
                    Address address = Address.toEntityBasic(addressRequest);
                    address.setCountry(country);
                    ReservationPerson reservationPerson = ReservationPerson.reservationPersonBasicEntity(personDataList.get(i));
                    reservationPerson.relationSet(savedReservationInfo,address);
                    // add
                    reservationPersonList.add(reservationPerson);
                }
                // saveAll
                reservationPersonRepository.saveAll(reservationPersonList);
            }
        }else {
            return ResponseEntity.badRequest().body("해당 여행일짜 없음");
        }
        return ResponseEntity.ok("예약 등록 완료");
    }

    @Transactional
    public ResponseEntity cancelReservation(Long reservationId) {
        reservationInfoRepository.deleteById(reservationId);
        return ResponseEntity.ok("별 다른 로직 없이 삭제만 했어요");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationInfoResponse.MyInfo>> myReservation(Long userId) {
//        필요정보 : 예약번호, 예약자, 예약 인원, 패키지명, 가격, 출발일, 도착일, 예약상태
        // 내 예약정보 전부 가져오기
        List<ReservationInfo> info = reservationInfoRepository.findByAccountId(userId);
        if(!info.isEmpty()){
            // 예약정보 있는경우: 응답값 초기화 및 필요한 객체들 불러오기
            List<ReservationInfoResponse.MyInfo> responseBody = new ArrayList<>();
            info.forEach(reservationInfo -> {
                ReservationInfoResponse.MyInfo myInfo = new ReservationInfoResponse.MyInfo();
                var reservationDate = reservationInfo.getReservationDate();
                var tripPackage = reservationDate.getTripPackage();
                var reservedPerson = reservationInfo.getReservationPerson();
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
            return ResponseEntity.badRequest().body(null);
        }

    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationPersonResponse.rpInfo>> getPeopleDataByInfoId(Long reservationId) {
        List<ReservationPerson> reservationPeople = reservationPersonRepository.findByReservationInfoId(reservationId);
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
