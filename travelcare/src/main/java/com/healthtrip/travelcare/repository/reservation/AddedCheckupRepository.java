package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.AddedCheckup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddedCheckupRepository extends JpaRepository<AddedCheckup, Long> {

    @Query("select ac from AddedCheckup ac " +
            "join fetch ac.medicalCheckupOptional mco " +
            "join fetch mco.medicalCheckupItem " +
//            "join ac.hospitalReservation hr " +
            "where ac.hospitalReservation.id = :reservationId")
    List<AddedCheckup> findByHospitalReservationId(@Param("reservationId") Long hospitalReservationID);
}