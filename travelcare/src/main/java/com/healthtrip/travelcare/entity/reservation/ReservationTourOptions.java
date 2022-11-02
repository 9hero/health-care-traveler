package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.dto.request.ReservationTourOptionsRequest;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class ReservationTourOptions extends BaseTimeEntity {
    @Builder
    public ReservationTourOptions(Long id, TourReservation tourReservation, TourOption tourOption, String day, Short manCount, String requesterName, BigDecimal amount) {
        this.id = id;
        this.tourReservation = tourReservation;
        this.tourOption = tourOption;
        this.day = day;
        this.manCount = manCount;
        this.requesterName = requesterName;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private TourReservation tourReservation;

    public void setTourReservation(TourReservation tourReservation) {
        this.tourReservation = tourReservation;
    }

    @ToString.Exclude
    @JoinColumn
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private TourOption tourOption;

    public void setTourOption(TourOption tourOption) {
        this.tourOption = tourOption;
    }

    private String day;

    private Short manCount;

    private String requesterName;

    private BigDecimal amount;

    public void setConfirmedAmount(BigDecimal newPrice) {
        this.amount = newPrice;
        this.tourReservation.addTourOptionAmount(newPrice);
    }

    public void updateByRequest(ReservationTourOptionsRequest updateRequest) {
        this.day = updateRequest.getDay();
        this.requesterName = updateRequest.getRequesterName();
        this.manCount = updateRequest.getManCount();
    }

    public void updateNewPrice(BigDecimal newPrice) {
        // oldPrice null 주의
        tourReservation.optionAmountUpdate(this.amount,newPrice); // oldPrice, newPrice
        this.amount = newPrice;
    }
}
