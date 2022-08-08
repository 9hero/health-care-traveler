package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TourReservationRepository extends JpaRepository<TourReservation,String> {
    boolean existsById(String id);

    List<TourReservation> findByAccountId(Long userId);

    @Query(value = "select a.email , ri.status as \"예약상태\",rp.first_name ,rp.last_name, \n" +
            "rd.depart_at as \"'출발일'\",rd.arrive_at as \"'도착일'\",tp.title as \"'여행패키지명'\"\n" +
            "from account a \n" +
            "inner join reservation_info ri on a.user_id = ri.user_id \n" +
            "inner join reservation_person rp on ri.id = rp.reservation_info_id \n" +
            "inner join reservation_date rd on rd.id = ri.date_id \n" +
            "inner join trip_package tp ON tp.id = rd.trip_package_id " +
            "where a.user_id = :userId",nativeQuery = true)
    Map<String, Object> findMyReservation(Long userId);

    List<TourReservation> getByAccountId(Long uid);

    @Query(value = "select ri from TourReservation ri where ri.id=:id and ri.account.id =:uid")
    TourReservation getByIdAndAccountId(String id, Long uid); // 사용시 account 까지 join시킴

}
