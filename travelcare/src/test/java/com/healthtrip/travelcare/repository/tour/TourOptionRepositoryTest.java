package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourOptionRepositoryTest {

    @Autowired
    private TourOptionRepository tourOptionRepository;

    private TourOption tourOption;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        tourOption = TourOption.builder()
                .optionName("렌트카")
                .build();
    }
    @Test
    @DisplayName("save")
    void 저장(){
        // given

        // when
        tourOptionRepository.save(tourOption);
        // then
        assertThat(tourOption.getId()).isNotNull();
    }
}