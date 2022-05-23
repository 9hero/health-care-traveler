package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.ReservationInfo;
import com.healthtrip.travelcare.repository.*;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationInfoServiceTest {

    @Autowired
    ReservationInfoService reservationInfoService;

    private final AccountsRepository accountsRepository;
    private final AccountCommonRepository commonRepository;
    private final ReservationInfoRepository reservationInfoRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final ReservationPersonRepository reservationPersonRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public ReservationInfoServiceTest(AccountsRepository accountsRepository, AccountCommonRepository commonRepository, ReservationInfoRepository reservationInfoRepository, ReservationDateRepository reservationDateRepository, CountryRepository countryRepository, AddressRepository addressRepository, ReservationPersonRepository reservationPersonRepository) {
        this.accountsRepository = accountsRepository;
        this.commonRepository = commonRepository;
        this.reservationInfoRepository = reservationInfoRepository;
        this.reservationDateRepository = reservationDateRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.reservationPersonRepository = reservationPersonRepository;
    }


    @Test
    @Transactional
    @Rollback
    @Disabled
    void cancelReservationCascade() {
        reservationInfoService.cancelReservation(22L);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Transactional
    void selectMyInfo() {
        //        필요정보 : 예약자, 예약 인원, 패키지명, 가격, 출발일, 도착일, 예약상태
        // 내 예약정보 전부 가져오기
        List<ReservationInfo> info = reservationInfoRepository.findByAccountId(70L);
        if (!info.isEmpty()) {
            // 예약정보 있는경우: 응답값 초기화 및 필요한 객체들 불러오기
            List<ReservationInfoResponse.MyInfo> responseBody = new ArrayList<>();
            info.forEach(reservationInfo -> {
                ReservationInfoResponse.MyInfo myInfo = new ReservationInfoResponse.MyInfo();
                var reservationDate = reservationInfo.getReservationDate();
                var tripPackage = reservationDate.getTripPackage();
                var reservedPerson = reservationInfo.getReservationPerson();
                var representPerson = reservedPerson.get(0);

                // 예약자(임시로 대표자 이름만)
                myInfo.setPersonName(representPerson.getFirstName() + " " + representPerson.getLastName());
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

            responseBody.forEach(System.out::println);
        }
    }
}