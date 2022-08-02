package com.healthtrip.travelcare.domain.entity.travel;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.domain.entity.BaseTimeEntity;
import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationInfo;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Entity
@NoArgsConstructor
@Getter
@Table(name = "tour_payment")
@ToString(callSuper = true)
public class PackageTourPayment extends BaseTimeEntity implements Persistable<String> {

    @Builder
    public PackageTourPayment(String id, ReservationInfo reservationInfo, BigDecimal amount, String currency, String payType, LocalDateTime paymentDate) {
        this.id = id;
        this.reservationInfo = reservationInfo;
        this.amount = amount;
        this.currency = currency;
        this.payType = payType;
        this.paymentDate = paymentDate;
    }

    @Id
    private String id;
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "tour_reservation_id")
    private ReservationInfo reservationInfo;
    @Column(name = "paid_amount")
    private BigDecimal amount;
    private String currency;
    private String payType;

    private LocalDateTime paymentDate;

//    private String buyerName;
//    private String status;
//    private String receiptUrl;

    public String idGenerate(String type) {
        String id = CommonUtils.dateWithTypeIdGenerate(type);
        this.id = id;
        return this.id;
    }

    @Override
    public boolean isNew(){
        return this.getCreatedAt() == null;
    }


}
