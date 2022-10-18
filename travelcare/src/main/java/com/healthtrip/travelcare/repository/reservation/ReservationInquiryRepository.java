package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.tour.reservation.ReservationInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationInquiryRepository extends JpaRepository<ReservationInquiry,Long> {

    @Query("select ri from ReservationInquiry ri " +
            "left join fetch ri.reservationInquiryChat ric " +
            "join ri.reservation r " +
            "join r.account a " +
            "where r.id = :reservationId and a.id=:accountId")
    ReservationInquiry getByReservationIdAndAccountId(@Param("reservationId")String reservationId, @Param("accountId")Long accountId);

    @Query("select ri from ReservationInquiry ri " +
            "join fetch ri.reservation r " +
            "join r.account a " +
            "where a.id=:accountId")
    List<ReservationInquiry> findByAccountId(@Param("accountId")Long accountId);

    @Query("select ri from ReservationInquiry ri " +
            "left join fetch ri.reservationInquiryChat " +
            "join ri.reservation r " +
            "join r.account a " +
            "where ri.id = :id and a.id=:accountId")
    ReservationInquiry getByIdAndAccountId(@Param("id") Long id, @Param("accountId")Long accountId);

    @Query("select ri from ReservationInquiry ri " +
            "join fetch ri.reservation r ")
    List<ReservationInquiry> findAllForAdmin();

    @Query("select ri from ReservationInquiry ri " +
            "left join fetch ri.reservationInquiryChat " +
            "where ri.id = :id")
    ReservationInquiry getByIdForAdmin(@Param("id") Long id);

}
