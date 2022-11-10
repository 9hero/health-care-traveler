package com.healthtrip.travelcare.repository.reservation;

import com.healthtrip.travelcare.entity.reservation.ReservationInquiryChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationInquiryChatRepo extends JpaRepository<ReservationInquiryChat,Long> {
    Optional<ReservationInquiryChat> findByIdAndAccountId(Long id, Long authenticatedUserId);
}
