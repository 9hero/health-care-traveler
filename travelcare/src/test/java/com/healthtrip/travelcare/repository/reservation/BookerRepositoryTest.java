package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaUnitTest
class BookerRepositoryTest {

    @Autowired
    private BookerRepository bookerRepository;

    private Booker booker;

    private EntityProvider entityProvider;

    @BeforeEach
    void setup(){
        entityProvider = new EntityProvider();
        booker = entityProvider.getBooker();
    }
    @Test
    @DisplayName("저장")
    void save(){
        // given
//        booker.getReservation().idGenerate();
        // when
        bookerRepository.save(booker);
        // then
        assertThat(booker.getId()).isNotNull();
        assertThat(booker.getReservation().getId()).isNotNull();
    }
}