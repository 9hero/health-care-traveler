package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.CustomTravelBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomTravelBoardRepository extends JpaRepository<CustomTravelBoard,Long> {

    List<CustomTravelBoard> findByReservationInfoId(Long reservationId);

    @Query("select ctb from CustomTravelBoard ctb " +
            "join ctb.reservationInfo ri " +
            "where ctb.reservationInfo.id =:reservationId" +
            " and ri.account.id = :uid")
    List<CustomTravelBoard> findByReservationInfoIdAndUserId(Long reservationId,Long uid);

    @Query("select ctb from CustomTravelBoard ctb join ctb.reservationInfo ri " +
            "where ctb.id = :id and ri.account.id = :uid")
    CustomTravelBoard findByIdAndAccountId(Long id, Long uid);
}
