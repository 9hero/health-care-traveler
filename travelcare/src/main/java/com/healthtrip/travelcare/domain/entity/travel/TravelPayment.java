package com.healthtrip.travelcare.domain.entity.travel;

import com.healthtrip.travelcare.domain.entity.BaseTimeEntity;
import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class TravelPayment extends BaseTimeEntity {

    @Builder
    public TravelPayment(String merchantUid, ReservationInfo reservationInfo, BigDecimal amount, String currency, String payMethod, String buyerName, String status, String receiptUrl) {
        this.merchantUid = merchantUid;
        this.reservationInfo = reservationInfo;
        this.amount = amount;
        this.currency = currency;
        this.payMethod = payMethod;
        this.buyerName = buyerName;
        this.status = status;
        this.receiptUrl = receiptUrl;
    }

    @Column(name = "id")
    private String merchantUid;
    private ReservationInfo reservationInfo;
    private BigDecimal amount;
    private String currency;
    private String payMethod;
    private String buyerName;
    private String status;
    private String receiptUrl;
}
