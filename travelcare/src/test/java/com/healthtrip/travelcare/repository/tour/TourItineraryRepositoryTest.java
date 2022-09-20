package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourItineraryRepositoryTest {


    @Autowired
    private TourItineraryRepository tourItineraryRepository;

    private TourItinerary tourItinerary;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        tourItinerary = TourItinerary.builder()
                .day("1일차")
                .accommodation("5성 호텔")
                .details("세부적일정입니다")
                .notice("유의사항입니다")
                .location("해운대구")
                .specificLocations("해동용궁사, 해운대바다, ~~ 등")
                .build();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        tourItinerary = TourItinerary.builder()
                .tourPackage(entityProvider.getTourPackage())
                .day("1일차")
                .accommodation("5성 호텔")
                .details("세부적일정입니다")
                .notice("유의사항입니다")
                .location("해운대구")
                .specificLocations("해동용궁사, 해운대바다, ~~ 등")
                .build();
        // when
        tourItineraryRepository.save(tourItinerary);
        // then
        assertThat(tourItinerary.getId()).isNotNull();
    }
}