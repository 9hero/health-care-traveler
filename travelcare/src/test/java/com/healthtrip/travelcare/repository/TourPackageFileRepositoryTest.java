package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
@Disabled
class TourPackageFileRepositoryTest {

    @Autowired
    TourPackageFileRepository tourPackageFileRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TourPackageRepository tourPackageRepository;

    @Transactional
    @Test
    void SaveAndFind() {
        var tp = TourPackage.builder()
                .description("파일레포 테스트")
                .title("간단한 패키지 제목")
                .account(accountsRepository.findAll().get(0))
                .price(BigDecimal.valueOf(1399.1563))
                .type("테스트")
                .tourPackageFileList(new ArrayList<TourPackageFile>())
                .build();
        var savedTp = tourPackageRepository.save(tp);
        var tpf= TourPackageFile.builder()
                .tourPackage(savedTp)
                .fileName("파일의 이름")
                .fileSize(1223)
                .originalName("원래이름")
                .url("대충 url")
                .build();

        tourPackageFileRepository.save(tpf);
        tourPackageFileRepository.flush();

        tourPackageFileRepository.findAll().forEach(System.out::println);
    }
}