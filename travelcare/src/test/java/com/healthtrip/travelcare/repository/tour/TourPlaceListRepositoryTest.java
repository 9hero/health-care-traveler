package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceList;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourPlaceListRepositoryTest {

    @Autowired
    private TourPlaceListRepository tourPlaceListRepository;

    private TourPlaceList tourPlaceList;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        tourPlaceList = TourPlaceList.builder()
                .tourPlace(entityProvider.getTourPlace())
                .tourItineraryElement(entityProvider.getTourItineraryElement())
                .showType(TourPlaceList.ShowType.THREE)
                .build();
        // when
        tourPlaceListRepository.save(tourPlaceList);
        // then
        assertThat(tourPlaceList.getId()).isNotNull();
        assertThat(tourPlaceList.getTourPlace().getId()).isNotNull();
        assertThat(tourPlaceList.getTourItineraryElement().getId()).isNotNull();
    }
}