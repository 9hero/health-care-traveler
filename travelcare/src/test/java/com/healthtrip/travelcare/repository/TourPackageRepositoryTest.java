package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class TourPackageRepositoryTest {

    @Autowired
    TourPackageRepository repository;

    @Test
    @Rollback
    @Disabled
    void findAllNullTest() {
        List<TourPackage> tourPackageList = repository.findAll();
        System.out.println("못찾아오면 널인지 : "+ tourPackageList.get(0) == null);
    }


}