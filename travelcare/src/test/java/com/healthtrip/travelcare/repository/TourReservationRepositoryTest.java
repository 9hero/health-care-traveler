package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourReservationRepositoryTest {

    @Autowired
    private TourReservationRepository tourReservationRepository;

    @Autowired
    static AccountsRepository accountsRepository;
    @Autowired
    static TourPackageRepository tourPackageRepository;
    @Autowired
    static TourPackageDateRepository tourPackageDateRepository;

    @BeforeAll
    @Transactional
    static void setRelationalEntity() {
        var account = accountsRepository.save(Account.builder()
                .email("test@num1")
                .status(Account.Status.Y)
                .password("1234")
                .userRole(Account.UserRole.ROLE_COMMON)
                .build());
        var tourPackage= tourPackageRepository.save(TourPackage.builder()
                        .account(account)
                        .title("testTitle")
                        .description("test")
                        .price(BigDecimal.TEN)
                        .type("test")
                .build());
        tourPackageDateRepository.save(
                TourPackageDate.builder()
                        .tourPackage(tourPackage)
                        .departAt(LocalDateTime.now())
                        .currentNumPeople((short)5)
                        .arriveAt(LocalDateTime.now().plusDays(1L))
                        .peopleLimit((short) 30)
                        .build()
        );
    }

    @Test
    @DisplayName("Entity save & find")
    void saveAndFind() {
        //given
        var acc=accountsRepository.getById(1L);
        var tpd = tourPackageDateRepository.getById(1L);
        var tr = TourReservation.builder()
                .account(acc)
                .tourPackageDate(tpd)
                .amount(BigDecimal.TEN)
                .csStatus(TourReservation.CsStatus.K)
                .paymentStatus(TourReservation.PaymentStatus.N)
                .status(TourReservation.Status.Y)
                .personCount((short) 1)
                .build();
        tr.idGenerate();
        var str = tourReservationRepository.save(tr);

        tourReservationRepository.flush();
        tourReservationRepository.findById(str.getId()).ifPresent(tourReservation -> System.out.println(tourReservation));
    }

    @Test
    void existsById() {
    }

    @Test
    void findByAccountId() {
    }

    @Test
    void getByIdAndAccountId() {
    }
}