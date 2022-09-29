package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import com.healthtrip.travelcare.repository.dto.response.ReservationDtoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Query("select r from Reservation r where r.account.id = ?1")
    List<Reservation> findByIdAccountId(@NonNull Long id, Pageable pageable);

    @Query("select r from Reservation r " +
            "left join fetch r.hospitalReservation " +
            "left join fetch r.tourReservation where r.account.id = :id")
    List<Reservation> findMyReservations(@Param ("id")@NonNull Long id);
    @Query("select distinct r from Reservation r " +
            "left join fetch r.hospitalReservation " +
            "left join fetch r.tourReservation " +
            "left join fetch r.bookers " +
            "where r.account.id = :accountId " +
            "and r.id = :reservationId")
    Reservation findMyReservationInfo(@Param("reservationId") String reservationId,@Param ("accountId")@NonNull Long accountId);

}