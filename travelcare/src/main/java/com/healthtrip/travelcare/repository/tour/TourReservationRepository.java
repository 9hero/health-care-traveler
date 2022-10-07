package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TourReservationRepository extends JpaRepository<TourReservation,Long> {
    boolean existsById(Long id);



}
