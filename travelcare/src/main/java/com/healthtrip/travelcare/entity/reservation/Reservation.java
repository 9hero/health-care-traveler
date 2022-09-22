package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseTimeEntity implements Persistable<String> {
    @Builder
    public Reservation(String id, Short manCount, Status status, BigDecimal amount, PaymentStatus paymentStatus, List<Booker> booker, TourReservation tourReservation, HospitalReservation hospitalReservation, Account account, List<TourOption> tourOption) {
        this.id = id;
        this.manCount = manCount;
        this.status = status;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.booker = booker;
        this.tourReservation = tourReservation;
        this.hospitalReservation = hospitalReservation;
        this.account = account;
        this.tourOption = tourOption;
    }

    @Id
    private String id;

    private Short manCount;
    // 결제상태: N: 결제전 Y: 결제완료 R: 환불완료
    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany
    private List<Booker> booker;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private TourReservation tourReservation;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private HospitalReservation hospitalReservation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;
    @OneToMany(mappedBy = "")
    private List<TourOption> tourOption;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean isNew(){
        return this.getCreatedAt() == null;
    }


    // 예약상태
    public enum Status{
        @Schema(description = "예약 가능")
        Y,
        @Schema(description = "예약 불가능")
        N,
        @Schema(description = "답변전:Before")
        B
    }
    public void paid() {
        this.paymentStatus = PaymentStatus.Y;
    }
    public enum PaymentStatus{
        @Schema(description = "결제전")
        N,
        @Schema(description = "결제완료")
        Y,
        @Schema(description = "환불완료")
        R
    }

    public String idGenerate() {
        String id = CommonUtils.dateWithTypeIdGenerate("R");
        this.id = id;
        return this.id;
    }
}
