package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.entity.tour.ReservationPayment;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private String paymentId;

    @Column(name = "paid_amount")
    private BigDecimal amount;

    private String currency;

    private String payType;

    private LocalDateTime paymentDate;

}
