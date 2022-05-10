package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.TripPackage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class TripPackageRepositoryTest {

    @Autowired
    TripPackageRepository repository;

    @Test
    @Rollback
    @Disabled
    void findAllNullTest() {
        List<TripPackage> tripPackageList = repository.findAll();
        System.out.println("못찾아오면 널인지 : "+tripPackageList.get(0) == null);
    }


}