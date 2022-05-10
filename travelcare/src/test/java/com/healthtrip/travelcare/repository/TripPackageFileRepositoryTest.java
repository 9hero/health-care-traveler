package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.TripPackage;
import com.healthtrip.travelcare.domain.entity.TripPackageFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripPackageFileRepositoryTest {

    @Autowired
    TripPackageFileRepository tripPackageFileRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TripPackageRepository tripPackageRepository;

    @Transactional
    @Test
    void SaveAndFind() {
        var tp = TripPackage.builder()
                .description("파일레포 테스트")
                .title("간단한 패키지 제목")
                .account(accountsRepository.findById(4L).get())
                .price(BigDecimal.valueOf(1399.1563))
                .type("테스트")
                .tripPackageFileList(new ArrayList<TripPackageFile>())
                .build();
        var savedTp = tripPackageRepository.save(tp);
        var tpf= TripPackageFile.builder()
                .tripPackage(savedTp)
                .fileName("파일의 이름")
                .fileSize(1223)
                .originalName("원래이름")
                .url("대충 url")
                .build();

        tripPackageFileRepository.save(tpf);
        tripPackageFileRepository.flush();


    }
}