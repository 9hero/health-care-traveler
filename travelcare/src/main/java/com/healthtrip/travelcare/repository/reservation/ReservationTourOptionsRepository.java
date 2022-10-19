package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationTourOptionsRepository extends JpaRepository<ReservationTourOptions, Long> {

    @Query("select rto from ReservationTourOptions rto " +
            "join fetch rto.tourReservation " +
            "where rto.id = :id")
    ReservationTourOptions findByIdWithReservation(@Param("id") Long id);
}