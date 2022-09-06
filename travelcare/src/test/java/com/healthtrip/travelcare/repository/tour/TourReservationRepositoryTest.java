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
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    TourPackageRepository tourPackageRepository;
    @Autowired
    TourPackageDateRepository tourPackageDateRepository;

    Account account;
    TourPackage tourPackage;
    TourPackageDate tourPackageDate;
    TourReservation tourReservation;
    @BeforeEach
    void setup() {
        account =Account.builder()
                .email("test@num1")
                .status(Account.Status.Y)
                .password("1234")
                .userRole(Account.UserRole.ROLE_COMMON)
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
    }

    void saveEntities() {
        accountsRepository.save(account);
        tourPackageRepository.save(tourPackage);
        tourPackageDateRepository.save(tourPackageDate);
    }

    @Test
    @DisplayName("저장: 성공")
    @Transactional
    void save() {
        // given
        saveEntities();
        tourReservation.idGenerate();

        // when
        TourReservation savedTourReservation  = tourReservationRepository.save(tourReservation);

        //then
        assertThat(savedTourReservation.getId()).isNotNull();
        assertThat(savedTourReservation.getAccount()).isNotNull();
        assertThat(savedTourReservation.getTourPackageDate()).isNotNull();
    }
    @DisplayName("주문코드 중복테스트")
    @Test
    void uniqueConflict() {
        // given
        saveEntities();
        List<TourReservation> tourReservations = new ArrayList<>();
        for (int i = 0; i<10;i++){
            tourReservations.add(TourReservation.builder()
                    .id(LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+"RV"+i)
                    .account(account)
                    .tourPackageDate(tourPackageDate)
                    .amount(BigDecimal.TEN)
                    .csStatus(TourReservation.CsStatus.K)
                    .paymentStatus(TourReservation.PaymentStatus.N)
                    .status(TourReservation.Status.Y)
                    .personCount((short) 1)
                    .build());
        }
        tourReservationRepository.saveAll(tourReservations);

        int counter = 0;

        // when
        for (int k = 0; k<4;k++){
            boolean conflict = tourReservationRepository.existsById(tourReservation.TESTidGenerate());
            if (!conflict) {
                tourReservationRepository.save(tourReservation);
                counter = 0;
                break;
            }else {
                counter++;
            }
        }
        // then
        assertThat(counter).isEqualTo(4);
    }
}