package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourReservationPersonRepository extends JpaRepository<TourBooker,Long> {

    List<TourBooker> findByTourReservationId(Long reservationId);

    @Query(value = "select distinct rp from TourBooker rp " +
            "join fetch rp.tourReservation ri " +
            "where ri.account.id =:accountId and ri.id =:reservationId")
    List<TourBooker> findByTourReservationId(@Param("reservationId") String reservationId, @Param("accountId") Long accountId);

}
