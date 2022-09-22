package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.tour.reservation.CustomTravelBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomTravelBoardRepository extends JpaRepository<CustomTravelBoard,Long> {

    List<CustomTravelBoard> findByTourReservationId(Long reservationId);

//    @Query("select ctb from CustomTravelBoard ctb " +
//            "join ctb.tourReservation ri " +
//            "where ctb.tourReservation.id =:reservationId" +
//            " and ri.account.id = :uid")
//    List<CustomTravelBoard> findByTourReservationIdAndUserId(Long reservationId, Long uid);

//    @Query("select ctb from CustomTravelBoard ctb join ctb.tourReservation ri " +
//            "where ctb.id = :id and ri.account.id = :uid")
//    CustomTravelBoard findByIdAndAccountId(Long id, Long uid);
}
