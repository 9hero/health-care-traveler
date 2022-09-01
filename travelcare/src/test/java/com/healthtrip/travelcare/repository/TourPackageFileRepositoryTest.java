package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import com.healthtrip.travelcare.service.TourPackageFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class TourPackageFileRepositoryTest {

    @Autowired
    TourPackageFileRepository tourPackageFileRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TourPackageRepository tourPackageRepository;

    Account account;
    TourPackage tourPackage;
    TourPackageFile tourPackageFile;
    String uniqueFileName =CommonUtils.buildFileName("파일의이름.확장자", "images/trip-package");
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
        tourPackageFile= TourPackageFile.builder()
                .tourPackage(tourPackage)
                .fileName(uniqueFileName)
                .fileSize(1223)
                .originalName("파일의이름")
                .url("대충 url")
                .build();
    }

    void setEntities() {
        accountsRepository.save(account);
        tourPackageRepository.save(tourPackage);
    }
    @Test
    @DisplayName("저장")
    void save() {
        // given
        setEntities();

        // when
        TourPackageFile savedTourPackageFile = tourPackageFileRepository.save(tourPackageFile);

        // then
        assertThat(savedTourPackageFile.getId()).isGreaterThan(0);
        assertThat(savedTourPackageFile.getCreatedAt()).isNotNull();
        assertThat(savedTourPackageFile.getFileName()).isEqualTo(uniqueFileName);
    }
    @DisplayName("조회")
    @Test
    void find(){
        // given
        setEntities();
        tourPackageFileRepository.save(tourPackageFile);
        // when
        TourPackageFile savedTourPackageFile = tourPackageFileRepository.findById(tourPackageFile.getId()).get();

        // then
        assertThat(savedTourPackageFile.getId()).isEqualTo(tourPackageFile.getId());
        assertThat(savedTourPackageFile.getUrl()).isEqualTo(tourPackageFile.getUrl());
        assertThat(savedTourPackageFile.getFileName()).isEqualTo(uniqueFileName);
    }
}