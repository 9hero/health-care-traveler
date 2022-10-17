package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class TourItineraryElementRepositoryTest {


    @Autowired
    private TourItineraryElementRepository tourItineraryElementRepository;

    private TourItineraryElement tourItineraryElement;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        tourItineraryElement = TourItineraryElement.builder()
                .tourItinerary(entityProvider.getTourItinerary())
                .sequence((short)1)
                .title("해운대 출발")
                .elementType(TourItineraryElement.ElementType.MOVE)
                .build();
        // when
        tourItineraryElementRepository.save(tourItineraryElement);

        // then
        assertThat(tourItineraryElement.getId()).isNotNull();
    }
}