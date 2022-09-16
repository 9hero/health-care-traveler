package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.account.AccountAgent;
import com.healthtrip.travelcare.entity.account.Address;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.account.AccountAgentRepository;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import com.healthtrip.travelcare.repository.location.AddressRepository;
import com.healthtrip.travelcare.repository.location.CountryRepository;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
@Disabled
class TourBookerRepositoryTest {

    @Autowired
    TourReservationPersonRepository tourReservationPersonRepository;

    private EntityProvider entityProvider;
    private TourBooker tourBooker;

    @BeforeEach
    void setup() {
        entityProvider = new EntityProvider();
        tourBooker = entityProvider.getTourBooker();
//        tourBooker = TourBooker.builder()
//                .tourReservation(tourReservation)
//                .address(tourBookerAddress)
//                .birth(LocalDate.now())
//                .emergencyContact("1111")
//                .phone("1111")
//                .lastName("james")
//                .firstName("borne")
//                .gender(PersonData.Gender.M)
//                .build();
    }

    void saveEntities() {
    }

    @Test
    @DisplayName("저장")
    void save() {
        // given
        saveEntities();
        // when
        TourBooker savedTourBooker =tourReservationPersonRepository.save(tourBooker);
        // then
        assertThat(savedTourBooker.getId()).isGreaterThan(0);
        assertThat(savedTourBooker.getCreatedAt()).isNotNull();
        assertThat(savedTourBooker.getTourReservation()).isNotNull();
    }
    @DisplayName("조회")
    @Test
    void find(){
        // given
        
        // when
        
        // then
        
    }
}