package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.account.AccountAgent;
import com.healthtrip.travelcare.entity.location.Address;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservationPerson;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.account.AccountAgentRepository;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import com.healthtrip.travelcare.repository.location.AddressRepository;
import com.healthtrip.travelcare.repository.location.CountryRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationPersonRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class TourReservationPersonRepositoryTest {

    @Autowired
    TourReservationPersonRepository tourReservationPersonRepository;

    @Autowired
    TourReservationRepository tourReservationRepository;
    @Autowired
    AccountAgentRepository accountAgentRepository;
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    TourPackageRepository tourPackageRepository;
    @Autowired
    TourPackageDateRepository tourPackageDateRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CountryRepository countryRepository;

    Country country;

    Address address;

    Account account;
    TourPackage tourPackage;
    TourPackageDate tourPackageDate;
    TourReservation tourReservation;
    TourReservationPerson tourReservationPerson;
    AccountAgent accountAgent;
    @BeforeEach
    void setup() {
        country = Country.builder()
                .name("USA")
                .build();
        address = Address.builder()
                .country(country)
                .city("new york")
                .district("somewhere")
                .address("anywhere")
                .addressDetail("")
                .postalCode("12345")
                .build();
        account =Account.builder()
                .email("test@num1")
                .status(Account.Status.Y)
                .password("1234")
                .userRole(Account.UserRole.ROLE_COMMON)
                .build();
        accountAgent = AccountAgent.builder()
                .account(account)
                .name("The-Air")
                .companyNumber("12345")
                .build();
        tourPackage= TourPackage.builder()
                .account(account)
                .title("testTitle")
                .description("test")
                .price(BigDecimal.TEN)
                .type("test")
                .build();
        tourPackageDate =
                TourPackageDate.builder()
                        .tourPackage(tourPackage)
                        .departAt(LocalDateTime.now())
                        .currentNumPeople((short)5)
                        .arriveAt(LocalDateTime.now().plusDays(1L))
                        .peopleLimit((short) 30)
                        .build();
        tourReservation = TourReservation.builder()
                .account(account)
                .tourPackageDate(tourPackageDate)
                .amount(BigDecimal.TEN)
                .csStatus(TourReservation.CsStatus.K)
                .paymentStatus(TourReservation.PaymentStatus.N)
                .status(TourReservation.Status.Y)
                .personCount((short) 1)
                .build();
        tourReservationPerson = TourReservationPerson.builder()
                .tourReservation(tourReservation)
                .address(address)
                .birth(LocalDate.now())
                .emergencyContact("1111")
                .phone("1111")
                .lastName("james")
                .firstName("borne")
                .gender(PersonData.Gender.M)
                .build();
    }

    void saveEntities() {
        countryRepository.save(country);
        addressRepository.save(address);
        accountsRepository.save(account);
        accountAgentRepository.save(accountAgent);
        tourPackageRepository.save(tourPackage);
        tourPackageDateRepository.save(tourPackageDate);
        tourReservation.idGenerate();
        tourReservationRepository.save(tourReservation);
    }

    @Test
    @DisplayName("저장")
    void save() {
        // given
        saveEntities();
        // when
        TourReservationPerson savedTourReservationPerson=tourReservationPersonRepository.save(tourReservationPerson);
        // then
        assertThat(savedTourReservationPerson.getId()).isGreaterThan(0);
        assertThat(savedTourReservationPerson.getCreatedAt()).isNotNull();
        assertThat(savedTourReservationPerson.getTourReservation()).isNotNull();
    }
    @DisplayName("조회")
    @Test
    void find(){
        // given
        
        // when
        
        // then
        
    }
}