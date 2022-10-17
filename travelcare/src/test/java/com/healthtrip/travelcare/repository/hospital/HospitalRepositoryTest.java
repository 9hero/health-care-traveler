package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.hospital.Hospital;
import com.healthtrip.travelcare.entity.hospital.HospitalAddress;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class HospitalRepositoryTest {

    @Autowired
    HospitalRepository hospitalRepo;
    EntityProvider entityProvider;
    HospitalAddress hospitalAddress;
    Hospital hospital;
    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        hospitalAddress = entityProvider.getHospitalAddress();
        hospital = Hospital.builder()
                .hospitalAddress(hospitalAddress)
                .name("병원 이름")
                .description("병원 설명")
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given

        // when
        hospitalRepo.save(hospital);
        // then
        assertThat(hospital.getId()).isNotNull();
        assertThat(hospital.getHospitalAddress().getId()).isNotNull();
    }

    @Test
    @DisplayName("임시 테스트")
    @Transactional
    @Disabled
    void temp(){
        var c = hospitalRepo.findAll();
        System.out.println(hospitalRepo.findById(131L).get());
        // given
        List<List<Long>> ides = new ArrayList<>();

        List<Long> ids1 = new ArrayList<>();
        ids1.add(125L);
        ids1.add(131L);

        ides.add(ids1);

        List<Long> ids2 = new ArrayList<>();
        ids2.add(125L);
        ids2.add(127L);
        ids2.add(129L);
        ids2.add(131L);

        ides.add(ids2);
        // when
        System.out.println(ides);
        var setIDs = ides.stream().distinct().collect(Collectors.toList());
        for (var g: setIDs){
        var a  = hospitalRepo.findAllById(g);
        System.out.println(a);
        }


    }
}