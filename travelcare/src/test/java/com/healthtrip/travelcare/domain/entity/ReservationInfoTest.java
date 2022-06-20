package com.healthtrip.travelcare.domain.entity;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
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
    @Rollback
    void saveAndFind(){
        ReservationInfo reservationInfo = ReservationInfo
                .builder()
                .account(accountsRepository.findAll().get(0))
                .reservationDate(reservationDateRepository.findAll().get(0))
                .personCount((short)5)
                .status(ReservationInfo.Status.Y)
                .build();
        repository.save(reservationInfo);
        repository.flush();
        repository.findAll().forEach(revInfo-> System.out.println(revInfo.getReservationDate()));
    }
}