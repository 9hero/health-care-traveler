package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.response.ReservationDtoResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationPersonResponse;
import com.healthtrip.travelcare.repository.dto.response.ReservationTourOptionsRes;
import com.healthtrip.travelcare.repository.dto.response.TourReservationDtoResponse;
import com.healthtrip.travelcare.repository.hospital.HospitalReservationRepository;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.reservation.BookerRepository;
import com.healthtrip.travelcare.repository.reservation.ReservationRepository;
import com.healthtrip.travelcare.repository.reservation.ReservationTourOptionsRepository;
import com.healthtrip.travelcare.repository.tour.TourOptionRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.tour.TourReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TourPackageRepository tourPackageRepository;
    private final TourReservationRepository tourReservationRepository;
    private final HospitalReservationRepository hospitalReservationRepository;
    private final AccountsRepository accountsRepository;

    private final TourOptionRepository tourOptionRepository;

    private final ReservationTourOptionsRepository addedTourOptionsRepository ;
    private final BookerRepository bookerRepository;

    public TourReservation reserveTour(ReservationRequest.TourR tourReserve) {
        var tourPackage = tourPackageRepository.findById(tourReserve.getPackageId())
                .orElseThrow(() -> {
                    throw new CustomException("패키지 찾을 수 없음", HttpStatus.BAD_REQUEST);
                });

        // 가격비교
        var tourPackagePrices = tourPackage.getPrices();
        var adultAmount = tourPackagePrices.getAdultPrice().multiply(BigDecimal.valueOf(tourReserve.getAdultCount()));
        var childAmount = tourPackagePrices.getChildPrice().multiply(BigDecimal.valueOf(tourReserve.getChildCount()));
        var infantAmount = tourPackagePrices.getInfantPrice().multiply(BigDecimal.valueOf(tourReserve.getInfantCount()));

        short peopleNum = (short) (tourReserve.getAdultCount()+
                tourReserve.getChildCount()+
                tourReserve.getInfantCount());

        int equals = tourReserve.getTourTotalAmount().compareTo(
                adultAmount.add(childAmount).add(infantAmount)
        );

        if (equals == 0){
            var tourReservation = TourReservation.builder()
                    .tourPackage(tourPackage)
                    .amount(tourReserve.getTourTotalAmount())
                    .personCount(peopleNum)
                    .reservedTime(LocalDateTime.now())
                    .build();
            return tourReservationRepository.save(tourReservation);
        }else {
            throw new CustomException("상품 가격과 예약 금액이 다릅니다.", HttpStatus.BAD_REQUEST);
        }
    }
    public HospitalReservation reserveHospital(ReservationRequest.HospitalR hospitalReserve) {
        return null;
    }


    @Transactional
    public void integrationReserve(ReservationRequest.Integration reservationDTO) {
        Account account = accountsRepository.getById(CommonUtils.getAuthenticatedUserId());

        // *주의* 가격비교 생략
        var reservation = Reservation.builder()
                .account(account)
                .title(reservationDTO.getReservationName())
                .amount(reservationDTO.getTotalAmount())
                .status(Reservation.Status.N)
                .manCount((short) reservationDTO.getBookerData().size())
                .paymentStatus(Reservation.PaymentStatus.N)
                .build();
        this.idGenerate(reservation);

        // 투어 예약
        if (reservationDTO.getTour() != null){
            reservation.setTourReservation(reserveTour(reservationDTO.getTour()));

        // 검진예약
        }else if (reservationDTO.getHospital() != null){
            reservation.setHospitalReservation(reserveHospital(reservationDTO.getHospital()));

        }else {
            throw new CustomException("예약 정보 없음",HttpStatus.BAD_REQUEST);
        }

        var savedReservation = reservationRepository.save(reservation);

        // 예약자 생성
        reservationDTO.getBookerData().forEach(personData -> {
            Booker booker = personData.toEntity();
            booker.setReservation(reservation);
        });

        /* 추가 옵션적용(선택검사, 투어옵션) */
        // 투어옵션
        var reservationTourOptionsList =
        reservationDTO.getTourOptions().stream().map(tourOptionsRequest -> {
            // 엔티티 변환
            ReservationTourOptions reservationTourOptions = tourOptionsRequest.toReservationTourOptions();

            // 투어옵션 항목 가져오기
            var tourOption = tourOptionRepository.getById(tourOptionsRequest.getTourOptionId());

            // 연관관계 설정
            reservationTourOptions.setTourOption(tourOption);
            reservationTourOptions.setReservation(reservation);
            return reservationTourOptions;
        }).collect(Collectors.toList());
        addedTourOptionsRepository.saveAll(reservationTourOptionsList);

        // 선택검사

    }

    private Reservation idGenerate(Reservation reservation) {
        for (int i = 0; i<4;i++){
            boolean conflict = reservationRepository.existsById(reservation.idGenerate());
            if (!conflict) {
                return reservationRepository.save(reservation);
            }else {
                if(i == 3){
                    log.error("예약번호 4연속 중복, 오늘자 :{}", LocalDateTime.now());
                    throw new CustomException("예약한도 초과에 가까움",HttpStatus.CONFLICT);
                }
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<ReservationDtoResponse> findMyReservation(Pageable pageable) {
       List<Reservation> reservationList= reservationRepository.findByIdAccountId(CommonUtils.getAuthenticatedUserId(),pageable);
       return reservationList.stream().map(ReservationDtoResponse::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReservationPersonResponse.rpInfo> getPeopleDataByReservationId(String reservationId) {
        Long uid = CommonUtils.getAuthenticatedUserId();
        List<Booker> reservationPeople = null;
//                bookerRepository.findReservationBookersById(reservationId,uid);
        var responseBody = reservationPeople.stream().map(person ->
                ReservationPersonResponse.rpInfo.builder()
                        .reservedPersonId(person.getId())
//                        .addressId(person.getBookerAddress().getId())
                        .birth(person.getBirth())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .gender(person.getGender())
                        .emergencyContact(person.getEmergencyContact())
                        .phone(person.getPhone())
                        .build()
        ).collect(Collectors.toList());
        return responseBody;
    }

    public ReservationDtoResponse.RVDetails findMyReservationDetail(String reservationId) {
        var reservationDetailsDTO = new ReservationDtoResponse.RVDetails();
        // 통합예약
        var reservation = reservationRepository.findMyReservationInfo(reservationId,CommonUtils.getAuthenticatedUserId());
        reservationDetailsDTO.setReservationDtoResponse(ReservationDtoResponse.toResponse(reservation));
        // 검진예약
//            reservation.getHospitalReservation();
        // 투어예약
        reservationDetailsDTO.setTourReservationDtoResponse(
            TourReservationDtoResponse.toResponse(
                    reservation.getTourReservation()
            )
        );
        // 예약자 명단
        reservationDetailsDTO.setBookerInfoList(
            reservation.getBookers().stream().map(ReservationPersonResponse.rpInfo::toResponse
            ).collect(Collectors.toList())
        );
        // 투어 추가 옵션
        reservationDetailsDTO.setTourOptions(
            reservation.getReservationTourOptions().stream().map(reservationTourOptions -> {
                var a = ReservationTourOptionsRes.toResponse(reservationTourOptions);
                a.setOptionName(reservationTourOptions.getTourOption().getOptionName());
                return a;
            }).collect(Collectors.toList())
        );

        return reservationDetailsDTO;
    }
}
