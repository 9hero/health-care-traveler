package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.entity.reservation.AddedCheckup;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.entity.reservation.ReservationTourOptions;
import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.BookerRequest;
import com.healthtrip.travelcare.repository.dto.response.*;
import com.healthtrip.travelcare.repository.hospital.HospitalReservationRepository;
import com.healthtrip.travelcare.repository.dto.request.ReservationRequest;
import com.healthtrip.travelcare.repository.hospital.MedicalCheckupOptionalRepo;
import com.healthtrip.travelcare.repository.hospital.MedicalCheckupProgramRepo;
import com.healthtrip.travelcare.repository.reservation.AddedCheckupRepository;
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
    private final AccountsRepository accountsRepository;
    private final ReservationRepository reservationRepository;
    private final BookerRepository bookerRepository;
    private final TourPackageRepository tourPackageRepository;
    private final TourReservationRepository tourReservationRepository;
    private final TourOptionRepository tourOptionRepository;
    private final ReservationTourOptionsRepository addedTourOptionsRepository ;

    private final HospitalReservationRepository hospitalReservationRepository;
    private final MedicalCheckupOptionalRepo medicalCheckupOptionalRepo;
    private final AddedCheckupRepository addedCheckupRepository;
    private final MedicalCheckupProgramRepo medicalCheckupProgramRepo;

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
        var medicalCheckupProgram = medicalCheckupProgramRepo.findById(hospitalReserve.getMedicalProgramId()).orElseThrow(() -> {
            throw new CustomException("", null);
        });
        // 병원예약
        HospitalReservation hospitalReservation = HospitalReservation.builder()
                .medicalCheckupProgram(medicalCheckupProgram)
                .reservedTime(hospitalReserve.getReservedAt())
                .manCount(hospitalReserve.getManCount())
                .amount(hospitalReserve.getHospitalTotalAmount())
                .build();
        var savedHospitalReservation = hospitalReservationRepository.save(hospitalReservation);
        // 선택검사추가
        var addedCheckups= medicalCheckupOptionalRepo.findAllById(hospitalReserve.getMedicalCheckUpOptionalIds())
                .stream().map(medicalCheckupOptional -> {
                    return AddedCheckup.builder()
                            .hospitalReservation(savedHospitalReservation)
                            .medicalCheckupOptional(medicalCheckupOptional)
                            .build();
                }).collect(Collectors.toList());
        addedCheckupRepository.saveAll(addedCheckups);

        // 가격비교

        return savedHospitalReservation;

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
        var savedReservation = reservationRepository.save(reservation);

        // 예약자 생성
        List<Booker> bookerList = reservationDTO.getBookerData().stream().map(BookerRequest::toEntity).collect(Collectors.toList());
        bookerList.forEach(booker -> {
            booker.setReservation(savedReservation);
        });
        bookerRepository.saveAll(bookerList);

        boolean isTour = reservationDTO.getTour() != null;
        boolean hospital = reservationDTO.getHospital() != null;
        // 투어 예약
        if (isTour){
            savedReservation.setTourReservation(reserveTour(reservationDTO.getTour()));
            var reservationTourOptionsList =
                    reservationDTO.getTourOptions().stream().map(tourOptionsRequest -> {
                        // 엔티티 변환
                        ReservationTourOptions reservationTourOptions = tourOptionsRequest.toReservationTourOptions();

                        // 투어옵션 항목 가져오기
                        var tourOption = tourOptionRepository.getById(tourOptionsRequest.getTourOptionId());

                        // 연관관계 설정
                        reservationTourOptions.setTourOption(tourOption);
                        reservationTourOptions.setReservation(savedReservation);
                        return reservationTourOptions;
                    }).collect(Collectors.toList());
            addedTourOptionsRepository.saveAll(reservationTourOptionsList);
        }

        // 검진예약
        if (hospital) {
            // 병원 예약자 확인
            var hospitalBookers = bookerList.stream().filter(Booker::isHospitalReserved).collect(Collectors.toList());

            // 예약
            HospitalReservation hospitalReservation = reserveHospital(reservationDTO.getHospital());
            savedReservation.setHospitalReservation(hospitalReservation);
        }
        if(!(isTour || hospital)){
            throw new CustomException("예약 정보 없음",HttpStatus.BAD_REQUEST);
        }


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
        // 병원예약
        if (reservation.getHospitalReservation()!=null){
            reservationDetailsDTO.setHospitalReservationDtoResponse(
                    HospitalReservationDtoResponse.toResponse(
                            reservation.getHospitalReservation()
                    )
            );
        }

        // 투어예약
        if (reservation.getTourReservation()!=null){

        reservationDetailsDTO.setTourReservationDtoResponse(
            TourReservationDtoResponse.toResponse(
                    reservation.getTourReservation()
            )
        );
        // 투어 추가 옵션
        reservationDetailsDTO.setTourOptions(
            reservation.getReservationTourOptions().stream().map(reservationTourOptions -> {
                var a = ReservationTourOptionsRes.toResponse(reservationTourOptions);
                a.setOptionName(reservationTourOptions.getTourOption().getOptionName());
                return a;
            }).collect(Collectors.toList())
        );
        }
        // 예약자 명단
        reservationDetailsDTO.setBookerInfoList(
            reservation.getBookers().stream().map(ReservationPersonResponse.rpInfo::toResponse
            ).collect(Collectors.toList())
        );
        return reservationDetailsDTO;
    }
}
