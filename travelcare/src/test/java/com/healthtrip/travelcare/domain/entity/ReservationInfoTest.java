package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.ReservationDateRepository;
import com.healthtrip.travelcare.repository.ReservationInfoRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
//@Disabled
class ReservationInfoTest {

    @Autowired
    ReservationInfoRepository repository;

    @Autowired
    ReservationDateRepository reservationDateRepository;

    @Autowired
    TripPackageRepository tripPackageRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Test
    @Transactional
    void find(){
        ReservationInfo reservationInfo = repository.findAll().get(0);
        System.out.println("trip pack : "+reservationInfo.getReservationDate()
                .getTripPackage());
        System.out.println("reserv date : "+reservationInfo.getReservationDate());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void saveAndFind(){
        ReservationInfo reservationInfo = ReservationInfo
                .builder()
                .id("123")
                .account(accountsRepository.getById(128L))
                .reservationDate(reservationDateRepository.getById(30L))
                .personCount((short)5)
                .status(ReservationInfo.Status.Y)
                .paymentStatus(ReservationInfo.PaymentStatus.N)
                .csStatus(ReservationInfo.CsStatus.K)
                .amount(BigDecimal.valueOf(555L))
                .build();
        var id = repository.save(reservationInfo).getId();
        repository.flush();
        System.out.println(repository.findById(id).get());
    }
}