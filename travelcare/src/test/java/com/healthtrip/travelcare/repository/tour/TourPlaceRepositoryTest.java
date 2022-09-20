package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlace;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourPlaceRepositoryTest {
    @Autowired
    private TourPlaceRepository tourPlaceRepository;

    private TourPlace tourPlace;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        tourPlace=TourPlace.builder()
                .placeName("장소명")
                .description("장소 설명")
                .summery("장소 짧은 소개")
                .tourPlaceImages(List.of(entityProvider.getTourPlaceImage()))
                .build();
        // when
        tourPlaceRepository.save(tourPlace);
        // then
        assertThat(tourPlace.getId()).isNotNull();
    }
}