package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.tour.reservation.TourBookerAddress;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import com.healthtrip.travelcare.repository.account.AccountCommonRepository;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AddressRequest;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.dto.response.ReservationInfoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import com.healthtrip.travelcare.repository.location.CountryRepository;
import com.healthtrip.travelcare.repository.tour.TourBookerAddressRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageDateRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationPersonRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationInfoService {

}
