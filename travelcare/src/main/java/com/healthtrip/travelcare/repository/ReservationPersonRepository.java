package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.ReservationPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationPersonRepository extends JpaRepository<ReservationPerson,Long> {

    List<ReservationPerson> findByReservationInfoId(Long reservationId);

    @Query(value = "select rp from ReservationPerson rp " +
            "join fetch rp.reservationInfo ri " +
            "where ri.account.id =:accountId and ri.id =:reservationId")
    List<ReservationPerson> findByReservationId(Long reservationId,Long accountId);

}
