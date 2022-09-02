package com.healthtrip.travelcare.entity.tour;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
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
    private static final String ID_TYPE = "TP";
    @Builder
    public PackageTourPayment(String id, TourReservation tourReservation, BigDecimal amount, String currency, String payType, LocalDateTime paymentDate) {
        this.id = id;
        this.tourReservation = tourReservation;
        this.amount = amount;
        this.currency = currency;
        this.payType = payType;
        this.paymentDate = paymentDate;
    }

    @Id
    private String id;
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tour_reservation_id")
    private TourReservation tourReservation;
    @Column(name = "paid_amount")
    private BigDecimal amount;
    private String currency;
    private String payType;

    private LocalDateTime paymentDate;

//    private String buyerName;
//    private String status;
//    private String receiptUrl;

    public String idGenerate() {
        String id = CommonUtils.dateWithTypeIdGenerate(ID_TYPE);
        this.id = id;
        return this.id;
    }
    public String testIdGenerate() {
        String id = CommonUtils.TESTdateWithTypeIdGenerate(ID_TYPE);
        this.id = id;
        return this.id;
    }

    @Override
    public boolean isNew(){
        return this.getCreatedAt() == null;
    }


}
