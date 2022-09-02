package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourPackageDateRepositoryTest {

    @Autowired
    TourPackageRepository tourPackageRepository;
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    TourPackageDateRepository tourPackageDateRepository;

    TourPackage tourPackage;
    Account account;
    TourPackageDate tourPackageDate;

    @BeforeEach
    void setup() {
        account = Account.builder()
                .email("Admin")
                .userRole(Account.UserRole.ROLE_ADMIN)
                .password("temp")
                .status(Account.Status.Y)
                .build();
        tourPackage = TourPackage.builder()
                .account(account)
                .description("test")
                .price(BigDecimal.valueOf(1234L))
                .title("test")
                .type("test")
                .build();
        tourPackageDate = TourPackageDate.builder()
                .tourPackage(tourPackage)
                .peopleLimit((short) 30)
                .currentNumPeople((short) 0)
                .arriveAt(LocalDateTime.now().plusDays(1L))
                .departAt(LocalDateTime.now())
                .build();
    }

    void setEntities() {
        accountsRepository.save(account);
        tourPackageRepository.save(tourPackage);
    }
    @DisplayName("저장")
    @Test
    void save(){
        // given
        setEntities();

        // when
        tourPackageDateRepository.save(tourPackageDate);

        // then
        assertThat(tourPackageDate.getId()).isGreaterThan(0);
        assertThat(tourPackageDate.getCreatedAt()).isNotNull();
    }
    @DisplayName("조회")
    @Test
    void find(){
        // given
        setEntities();
        TourPackageDate savedTourPackageDate = tourPackageDateRepository.save(tourPackageDate);

        // when
        TourPackageDate foundTourPackageDate = tourPackageDateRepository.findById(savedTourPackageDate.getId()).get();

        // then
        assertThat(foundTourPackageDate.getId()).isEqualTo(savedTourPackageDate.getId());
        assertThat(foundTourPackageDate.getTourPackage()).isEqualTo(savedTourPackageDate.getTourPackage());
    }
}