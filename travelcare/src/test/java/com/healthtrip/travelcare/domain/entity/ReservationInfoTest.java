package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.ReservationDateRepository;
import com.healthtrip.travelcare.repository.ReservationInfoRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setAccount(accountsRepository.getById(4L));
        reservationInfo.setReservationDate(reservationDateRepository.findAll().get(0));
        reservationInfo.setTripPackage(tripPackageRepository.findAll().get(0));
        reservationInfo.setPersonCount((short)5);
        reservationInfo.setStatus(ReservationInfo.Status.Y);
        repository.save(reservationInfo);
        repository.flush();
        repository.findAll().forEach(revInfo-> System.out.println(revInfo.getReservationDate()));
    }
}