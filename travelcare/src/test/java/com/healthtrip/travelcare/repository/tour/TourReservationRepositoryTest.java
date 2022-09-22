package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class TourReservationRepositoryTest {

    @Autowired
    TourReservationRepository tourReservationRepository;

    TourReservation tourReservation;
    @BeforeEach
    void setup() {
        EntityProvider entityProvider = new EntityProvider();
        tourReservation = entityProvider.getTourReservation();
    }


    @Test
    @DisplayName("저장: 성공")
    @Transactional
    void save() {
        // given

        // when
        TourReservation savedTourReservation  = tourReservationRepository.save(tourReservation);

        //then
        assertThat(savedTourReservation.getId()).isNotNull();
    }


}