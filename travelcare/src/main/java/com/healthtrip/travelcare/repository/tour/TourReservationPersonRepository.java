package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservationPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourReservationPersonRepository extends JpaRepository<TourReservationPerson,Long> {

    List<TourReservationPerson> findByTourReservationId(Long reservationId);

    @Query(value = "select distinct rp from TourReservationPerson rp " +
            "join fetch rp.tourReservation ri " +
            "where ri.account.id =:accountId and ri.id =:reservationId")
    List<TourReservationPerson> findByTourReservationId(@Param("reservationId") String reservationId, @Param("accountId") Long accountId);

}
