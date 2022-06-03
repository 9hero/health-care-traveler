package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.dto.request.ReservationDateRequest;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ReservationDate extends BaseTimeEntity{


    @Builder
    public ReservationDate(Long id, TripPackage tripPackage, LocalDateTime departAt, LocalDateTime arriveAt, short currentNumPeople, short peopleLimit) {
        this.id = id;
        this.tripPackage = tripPackage;
        this.departAt = departAt;
        this.arriveAt = arriveAt;
        this.currentNumPeople = currentNumPeople;
        this.peopleLimit = peopleLimit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_package_id")
    private TripPackage tripPackage;

    private LocalDateTime departAt;

    private LocalDateTime arriveAt;

    private short currentNumPeople;

    private short peopleLimit;

    public boolean plusCurrentPeopleNumber(Short currentNumPeople) {
        if(peopleLimit < (this.currentNumPeople + currentNumPeople)){
            return true;
        }else {
            this.currentNumPeople += currentNumPeople;
            return false;
        }
    }
    public void modifyEntity(ReservationDateRequest.Modify modifyReq){
        this.currentNumPeople=modifyReq.getCurrentNumPeople();
        this.peopleLimit= modifyReq.getPeopleLimit();
        this.departAt = modifyReq.getDepartAt();
        this.arriveAt = modifyReq.getArriveAt();
    }
}
