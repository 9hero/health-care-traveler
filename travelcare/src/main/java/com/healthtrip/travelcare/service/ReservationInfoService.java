package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.*;
import com.healthtrip.travelcare.repository.*;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        ReservationDate reservationDate = reservationDateRepository.getById(reserveData.getDateId());

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
            var addressData = reserveData.getAddressData();
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
        var peopleData = reserveData.getReservationPersonData();

        var reservationPerson = ReservationPerson.builder()
                .reservationInfo(savedReservationInfo)
                .firstName(peopleData.getFirstName())
                .lastName(peopleData.getLastName())
                .gender(peopleData.getGender())
                .birth(peopleData.getBirth())
                .phone(peopleData.getPhone())
                .emergencyContact(peopleData.getEmergencyContact())
                .address(savedAddress)
                .build();
        reservationPersonRepository.save(reservationPerson);
        return ResponseEntity.ok("예약 등록 완료");
    }

    @Transactional
    public ResponseEntity cancelReservation(Long reservationId) {
        reservationInfoRepository.deleteById(reservationId);
        return ResponseEntity.ok("별 다른 로직 없이 삭제만 했어요");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationInfoResponse.MyInfo>> myReservation(Long userId) {
//        필요정보 : 예약자, 예약 인원, 패키지명, 가격, 출발일, 도착일, 예약상태
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
}
