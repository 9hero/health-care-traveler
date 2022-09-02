package com.healthtrip.travelcare.entity.tour.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.dto.request.ReservationDateRequest;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "reservation_date")
public class TourPackageDate extends BaseTimeEntity {


    @Builder
    public TourPackageDate(Long id, TourPackage tourPackage, LocalDateTime departAt, LocalDateTime arriveAt, short currentNumPeople, short peopleLimit) {
        this.id = id;
        this.tourPackage = tourPackage;
        this.departAt = departAt;
        this.arriveAt = arriveAt;
        this.currentNumPeople = currentNumPeople;
        this.peopleLimit = peopleLimit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trip_package_id")
    private TourPackage tourPackage;

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
