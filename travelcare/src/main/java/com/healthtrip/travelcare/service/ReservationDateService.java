package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.dto.request.ReservationDateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationDateService {

    private final TourPackageRepository tourPackageRepository;
    private final TourPackageDateRepository dateRepository;

    @Transactional
    public String addTripDate(ReservationDateRequest.AddTrip addTripReq) {
        TourPackage tourPackage = tourPackageRepository.getById(addTripReq.getTripPackageId());

        TourPackageDate tourPackageDate = TourPackageDate.builder()
                .tourPackage(tourPackage)
                .departAt(addTripReq.getDepartAt())
                .arriveAt(addTripReq.getArriveAt())
                .peopleLimit(addTripReq.getPeopleLimit())
                .currentNumPeople(addTripReq.getCurrentNumPeople())
                .build();
         boolean saveCheck = dateRepository.save(tourPackageDate).getId() != null;
         if (saveCheck){
             return "ok";
         }else {
             return "저장실패";
         }
    }

    @Transactional
    public String modifyTripDates(ReservationDateRequest.Modify modifyReq) {
        TourPackageDate tourPackageDate = dateRepository.findById(modifyReq.getReservationDateId()).orElseThrow();
        tourPackageDate.modifyEntity(modifyReq);
        dateRepository.save(tourPackageDate);
        return "업데이트완료";
    }
}
