package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceImage;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class TourPlaceImageRepositoryTest {

    @Autowired
    private TourPlaceImageRepository tourPlaceImageRepository;

    private TourPlaceImage tourPlaceImage;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
    }

    @Test
    @DisplayName("저장")
    void save(){
        // given
        tourPlaceImage = TourPlaceImage.builder()
                .tourPlace(entityProvider.getTourPlace())
                .fileName("uniqueFileName")
                .fileSize(1223)
                .originalName("파일의이름")
                .url("대충 url")
                .build();

        // when
        tourPlaceImageRepository.save(tourPlaceImage);
        // then
        assertThat(tourPlaceImage.getId()).isNotNull();
    }
}