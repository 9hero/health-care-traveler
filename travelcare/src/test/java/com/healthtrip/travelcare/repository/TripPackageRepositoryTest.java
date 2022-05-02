package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.TripPackage;
import com.healthtrip.travelcare.repository.dto.response.TpResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void findByIdOption() {
        Optional<TripPackage> tripPackage = repository.findById(17L);

        ResponseEntity responseEntity;

        if (tripPackage.isPresent()){
             responseEntity = tripPackage.map(tpPackageEntity ->
                    ResponseEntity
                            .ok()
                            .body(TpResponseDto.entityToDto(tpPackageEntity)))
                            .get();

        }else{
            responseEntity = ResponseEntity.status(200).body("패키지를 찾을 수 없음");

        }
        /*
        System.out.println(responseEntity.getBody() + " 없을때");
        Assertions.assertEquals("패키지를 찾을 수 없음",responseEntity.getBody());


         */

    }
}