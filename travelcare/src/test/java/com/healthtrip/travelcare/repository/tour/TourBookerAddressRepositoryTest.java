package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.reservation.TourBookerAddress;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourBookerAddressRepositoryTest {

    @Autowired
    private TourBookerAddressRepository tourBookerAddressRepository;

    private TourBookerAddress tourBookerAddress;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        tourBookerAddress = entityProvider.getTourBookerAddress();
        // when
        tourBookerAddressRepository.save(tourBookerAddress);
        // then
        assertThat(tourBookerAddress.getId()).isNotNull();
    }
}