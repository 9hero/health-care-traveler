package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.hospital.HospitalReservationRepository;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final TourReservationRepository tourReservationRepository;
    private final HospitalReservationRepository hospitalReservationRepository;

    public void reserveTour(ReservationRequest.TourR tourReserve) {
    }

    public void reserveHospital(ReservationRequest.HospitalR hospitalReserve) {
    }


}
