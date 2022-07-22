package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.travel.reservation.ReservationDate;
import com.healthtrip.travelcare.domain.entity.travel.trip_package.TripPackage;
import com.healthtrip.travelcare.repository.ReservationDateRepository;
import com.healthtrip.travelcare.repository.TripPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.ReservationDateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationDateService {

    private final TripPackageRepository tripPackageRepository;
    private final ReservationDateRepository dateRepository;

    @Transactional
    public String addTripDate(ReservationDateRequest.AddTrip addTripReq) {
        TripPackage tripPackage = tripPackageRepository.getById(addTripReq.getTripPackageId());

        ReservationDate reservationDate = ReservationDate.builder()
                .tripPackage(tripPackage)
                .departAt(addTripReq.getDepartAt())
                .arriveAt(addTripReq.getArriveAt())
                .peopleLimit(addTripReq.getPeopleLimit())
                .currentNumPeople(addTripReq.getCurrentNumPeople())
                .build();
         boolean saveCheck = dateRepository.save(reservationDate).getId() != null;
         if (saveCheck){
             return "ok";
         }else {
             return "저장실패";
         }
    }

    @Transactional
    public String modifyTripDates(ReservationDateRequest.Modify modifyReq) {
        ReservationDate reservationDate = dateRepository.findById(modifyReq.getReservationDateId()).orElseThrow();
        reservationDate.modifyEntity(modifyReq);
        dateRepository.save(reservationDate);
        return "업데이트완료";
    }
}
