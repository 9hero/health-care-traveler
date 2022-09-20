package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.account.AccountCommonRepository;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import com.healthtrip.travelcare.repository.location.AddressRepository;
import com.healthtrip.travelcare.repository.location.CountryRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationPersonRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TourReservationServiceTest {

    @Autowired
    ReservationInfoService reservationInfoService;

    private final AccountsRepository accountsRepository;
    private final AccountCommonRepository commonRepository;
    private final TourReservationRepository tourReservationRepository;
    private final TourPackageDateRepository tourPackageDateRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final TourReservationPersonRepository tourReservationPersonRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public TourReservationServiceTest(AccountsRepository accountsRepository, AccountCommonRepository commonRepository, TourReservationRepository tourReservationRepository, TourPackageDateRepository tourPackageDateRepository, CountryRepository countryRepository, AddressRepository addressRepository, TourReservationPersonRepository tourReservationPersonRepository) {
        this.accountsRepository = accountsRepository;
        this.commonRepository = commonRepository;
        this.tourReservationRepository = tourReservationRepository;
        this.tourPackageDateRepository = tourPackageDateRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.tourReservationPersonRepository = tourReservationPersonRepository;
    }


    @Test
    @Transactional
    @Rollback
    @Disabled
    void cancelReservationCascade() {
        reservationInfoService.cancelReservation("22");
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Transactional
    @Disabled
    void selectMyInfo() {
        //        필요정보 : 예약자, 예약 인원, 패키지명, 가격, 출발일, 도착일, 예약상태
        // 내 예약정보 전부 가져오기
        List<TourReservation> info = tourReservationRepository.findByAccountId(70L);
        if (!info.isEmpty()) {
            // 예약정보 있는경우: 응답값 초기화 및 필요한 객체들 불러오기
            List<ReservationInfoResponse.MyInfo> responseBody = new ArrayList<>();
            info.forEach(reservationInfo -> {
                ReservationInfoResponse.MyInfo myInfo = new ReservationInfoResponse.MyInfo();
//                var reservationDate = reservationInfo.getTourPackageDate();
//                var tripPackage = reservationDate.getTourPackage();
                var reservedPerson = reservationInfo.getTourReservationPeople();
                var representPerson = reservedPerson.get(0);

                // 예약자(임시로 대표자 이름만)
                myInfo.setPersonName(representPerson.getFirstName() + " " + representPerson.getLastName());
                // 예약상태, 에약인원
                myInfo.setStatus(reservationInfo.getStatus());
                myInfo.setPersonCount(reservationInfo.getPersonCount());

                // 패키지명, 가격
//                myInfo.setPackageTitle(tripPackage.getTitle());
//                myInfo.setPrice(tripPackage.getPrice());

                // 출발일 도착일
//                myInfo.setDepartAt(reservationDate.getDepartAt());
//                myInfo.setArrivedAt(reservationDate.getArriveAt());

                responseBody.add(myInfo);
            });

            responseBody.forEach(System.out::println);
        }
    }

}