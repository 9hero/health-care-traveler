package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageFileRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

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
        EntityProvider entityProvider = new EntityProvider();
        account = entityProvider.getAccount();
        tourPackage = entityProvider.getTourPackage();
        tourPackageFile= TourPackageFile.builder()
                .tourPackage(tourPackage)
                .fileName(uniqueFileName)
                .fileSize(1223)
                .originalName("파일의이름2")
                .url("대충 url2")
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
        tourPackageFile= TourPackageFile.builder()
                .tourPackage(tourPackageRepository.getById(134L))
                .fileName(uniqueFileName)
                .fileSize(1223)
                .originalName("파일의이름")
                .url("대충 url")
                .build();
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