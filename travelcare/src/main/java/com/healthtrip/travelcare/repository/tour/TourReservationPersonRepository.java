package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourReservationPersonRepository extends JpaRepository<TourBooker,Long> {

    List<TourBooker> findByTourReservationId(Long reservationId);


}
