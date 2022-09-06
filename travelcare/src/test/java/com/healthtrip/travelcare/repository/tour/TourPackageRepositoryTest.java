package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class TourPackageRepositoryTest {

    @Autowired
    TourPackageRepository tourPackageRepository;

    @Autowired
    AccountsRepository accountsRepository;

    Account account;
    TourPackage tourPackage;

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
    }

    @DisplayName("저장")
    @Test
    void saveEntity(){
        // given
        Account savedAccount = accountsRepository.save(account);

        // when
        TourPackage savedTourPackage = tourPackageRepository.save(tourPackage);

        // then
        assertThat(savedTourPackage.getId()).isGreaterThan(0);
        assertThat(savedTourPackage.getPrice()).isEqualTo(BigDecimal.valueOf(1234L));
        assertThat(savedTourPackage.getCreatedAt()).isNotNull();
    }
    @DisplayName("조회")
    @Test
    void findEntity(){
        // given
        Account savedAccount = accountsRepository.save(account);
        TourPackage savedTourPackage = tourPackageRepository.save(tourPackage);

        // when
        TourPackage foundTourPackage = tourPackageRepository.findById(savedTourPackage.getId()).get();

        // then
        assertThat(foundTourPackage.getId()).isEqualTo(savedTourPackage.getId());
        assertThat(savedTourPackage.getPrice()).isEqualTo(BigDecimal.valueOf(1234L));
    }
}