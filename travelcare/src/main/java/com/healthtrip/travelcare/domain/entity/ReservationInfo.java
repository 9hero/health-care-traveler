package com.healthtrip.travelcare.domain.entity;

import javax.persistence.*;

import com.healthtrip.travelcare.repository.dto.response.CustomTravelResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicUpdate
@ToString(callSuper = true)
public class ReservationInfo extends BaseTimeEntity{
    @Builder
    public ReservationInfo(Long id, Account account, ReservationDate reservationDate, short personCount, List<ReservationPerson> reservationPerson, Status status) {
        this.id = id;
        this.account = account;
        this.reservationDate = reservationDate;
        this.personCount = personCount;
        this.reservationPerson = reservationPerson;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private Status status;

    public void customTravelOn() {
        this.status = Status.B;
    }
    public void customStatusUpdate(Status status){
        this.status = status;
    }
    public enum Status{
        @Schema(description = "예약 가능")
        Y,
        @Schema(description = "예약 불가능")
        N,
        @Schema(description = "답변전:Before")
        B
    }


}
