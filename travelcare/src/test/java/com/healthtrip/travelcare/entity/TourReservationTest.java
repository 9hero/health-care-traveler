package com.healthtrip.travelcare.entity;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.TourReservationRepository;
import com.healthtrip.travelcare.repository.TourPackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
//@Disabled
class TourReservationTest {

    @Autowired
    TourReservationRepository repository;

    @Autowired
    TourPackageDateRepository tourPackageDateRepository;

    @Autowired
    TourPackageRepository tourPackageRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Test
    @Transactional
    void find(){
        TourReservation tourReservation = repository.findAll().get(0);
        System.out.println("trip pack : "+ tourReservation.getTourPackageDate()
                .getTourPackage());
        System.out.println("reserv date : "+ tourReservation.getTourPackageDate());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void saveAndFind(){
        TourReservation tourReservation = TourReservation
                .builder()
                .id("123")
                .account(accountsRepository.getById(128L))
                .tourPackageDate(tourPackageDateRepository.getById(30L))
                .personCount((short)5)
                .status(TourReservation.Status.Y)
                .paymentStatus(TourReservation.PaymentStatus.N)
                .csStatus(TourReservation.CsStatus.K)
                .amount(BigDecimal.valueOf(555L))
                .build();
        var id = repository.save(tourReservation).getId();
        repository.flush();
        System.out.println(repository.findById(id).get());
    }
}