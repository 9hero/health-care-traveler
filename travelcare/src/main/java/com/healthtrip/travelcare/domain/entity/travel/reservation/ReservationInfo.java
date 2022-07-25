package com.healthtrip.travelcare.domain.entity.travel.reservation;

import javax.persistence.*;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.domain.entity.account.Account;
import com.healthtrip.travelcare.domain.entity.BaseTimeEntity;
import com.healthtrip.travelcare.domain.entity.travel.PackageTourPayment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicUpdate
@ToString(callSuper = true)
public class ReservationInfo extends BaseTimeEntity implements Serializable, Persistable<String> {

    @Builder
    public ReservationInfo(String id, Account account, ReservationDate reservationDate, short personCount, List<CustomTravelBoard> customTravelBoard, List<ReservationPerson> reservationPerson, Status status, PaymentStatus paymentStatus, CsStatus csStatus, BigDecimal amount) {
        this.id = id;
        this.account = account;
        this.reservationDate = reservationDate;
        this.personCount = personCount;
        this.customTravelBoard = customTravelBoard;
        this.reservationPerson = reservationPerson;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.csStatus = csStatus;
        this.amount = amount;
    }

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_id")
    @ToString.Exclude
    private ReservationDate reservationDate;

    private short personCount;

    @ToString.Exclude
    @OneToMany(mappedBy = "reservationInfo",fetch = FetchType.LAZY)
    private List<CustomTravelBoard> customTravelBoard;

    @ToString.Exclude
    @OneToMany(mappedBy = "reservationInfo",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<ReservationPerson> reservationPerson;
    // 예약 번호( 결제대행사에 결제내역 조회시 필요)

//    @ToString.Exclude
//    @OneToOne(mappedBy = "reservationInfo",fetch = FetchType.LAZY)
//    private PackageTourPayment packageTourPayment;

    @Enumerated(EnumType.STRING)
    private Status status;

    // 결제상태: N: 결제전 Y: 결제완료 R: 환불완료
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // 예약에 대한 고객 요청사항 C: 취소 R: 환불처리 완료
    @Enumerated(EnumType.STRING)
    private CsStatus csStatus;

    // 예약 금액
    private BigDecimal amount;
    public void customTravelOn() {
        this.status = Status.B;
    }
    public void customStatusUpdate(Status status){
        this.status = status;
    }

    @Override
    public boolean isNew() {
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
    // 고객 요청사항
    public enum CsStatus{
        @Schema(description = "cancel 예약취소(환불 전)")
        C,
        @Schema(description = "환불완료상태")
        R,
        @Schema(description = "요청없음")
        K
    }
    public enum PaymentStatus{
        @Schema(description = "결제전")
        N,
        @Schema(description = "결제완료")
        Y,
        @Schema(description = "환불완료")
        R
    }

    public String idGenerate(String type) {
        String id = CommonUtils.dateWithTypeIdGenerate("RV");
        this.id = id;
        return this.id;
    }
}
