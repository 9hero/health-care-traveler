package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.Booker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookerRepository extends JpaRepository<Booker, Long> {
//    @Query("select b from Booker b " +
//            "inner join b.reservation r " +
//            "on b.reservation.id = r.id " +
//            "inner join r.account a " +
//            "on a.id = r.account.id " +
//            "where b.reservation.id :reservationId r.account.id = :accountId")
//    List<Booker> findReservationBookersById(@Param("reservationId") String id,@Param("accountId") Long id1);
}
