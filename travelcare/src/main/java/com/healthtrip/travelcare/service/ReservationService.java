package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.hospital.HospitalReservation;
import com.healthtrip.travelcare.entity.reservation.*;
import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.*;
import com.healthtrip.travelcare.repository.dto.response.*;
import com.healthtrip.travelcare.repository.hospital.HospitalReservationRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ReservationTourOptionsRepository revTourOptionsRepository;
    private final BookerRepository bookerRepository;
    private final TourPackageRepository tourPackageRepository;
    private final TourReservationRepository tourReservationRepository;
    private final TourOptionRepository tourOptionRepository;
    private final ReservationTourOptionsRepository addedTourOptionsRepository ;

    private final HospitalReservationRepository hospitalReservationRepository;
    private final MedicalCheckupOptionalRepo medicalCheckupOptionalRepo;
    private final AddedCheckupRepository addedCheckupRepository;
    private final MedicalCheckupProgramRepo medicalCheckupProgramRepo;

    public TourReservation reserveTour(ReservationRequest.TourR tourReserve, Reservation savedReservation) {
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
            // 투어 예약
            var tourReservation = TourReservation.builder()
                    .tourPackage(tourPackage)
                    .amount(tourReserve.getTourTotalAmount())
                    .personCount(peopleNum)
                    .reservedTime(LocalDateTime.now())
                    .build();

            // 추가 옵션
            if (tourReserve.getTourOptions() != null){
                var reservationTourOptionsList =
                        tourReserve.getTourOptions().stream().map(tourOptionsRequest -> {
                            // 엔티티 변환
                            ReservationTourOptions reservationTourOptions = tourOptionsRequest.toReservationTourOptions();

                            // 투어옵션 항목 가져오기
                            var tourOption = tourOptionRepository.getById(tourOptionsRequest.getTourOptionId());

                            // 연관관계 설정
                            reservationTourOptions.setTourOption(tourOption);
                            reservationTourOptions.setTourReservation(tourReservation);
                            return reservationTourOptions;
                        }).collect(Collectors.toList());
                addedTourOptionsRepository.saveAll(reservationTourOptionsList);
            }

            return tourReservationRepository.save(tourReservation);
        }else {
            throw new CustomException("상품 가격과 예약 금액이 다릅니다.", HttpStatus.BAD_REQUEST);
        }
    }
    public HospitalReservation reserveHospital(ReservationRequest.HospitalR hospitalReserve, List<Booker> hospitalBookers) {
        var medicalCheckupProgram = medicalCheckupProgramRepo.findById(hospitalReserve.getMedicalProgramId()).orElseThrow(() -> {
            throw new CustomException("해당 검진 없음", HttpStatus.BAD_REQUEST);
        });
        // 남녀 카운트
        int manNumber = 0;
        int womanNumber = 0;
        for(Booker booker : hospitalBookers){
            if(booker.getGender().equals(Booker.Gender.M)){
                manNumber++;
            }else {
                womanNumber++;
            }
        }
        // 가격비교
        // checkupOption에서 끌어온 가격 * 예약인원 수 == 프론트가 보낸 값
        // + 프로그램 가격 * 남 + 여성프로그램 가격 * 여
        BigDecimal programPrice =
                medicalCheckupProgram.getPriceForMan().multiply(BigDecimal.valueOf(manNumber))
                        .add(medicalCheckupProgram.getPriceForWoman().multiply(BigDecimal.valueOf(womanNumber)));

        // 선택검진 가격추가 및 비교 결과 값
        BigDecimal addedCheckupAmount = BigDecimal.ZERO;

        // 병원예약
        HospitalReservation hospitalReservation = HospitalReservation.builder()
                .medicalCheckupProgram(medicalCheckupProgram)
                .reservedTime(hospitalReserve.getReservedAt())
                .personCount(hospitalReserve.getPersonCount())
                .build();
        var savedHospitalReservation = hospitalReservationRepository.save(hospitalReservation);

        // 선택검사추가
        var medicalCheckupOptionalReqList = hospitalReserve.getMedicalCheckUpOptionals();
        boolean additionalCheckupEmpty = (medicalCheckupOptionalReqList == null);
        if (!additionalCheckupEmpty){
            // 1. 벌크로 영속성에 저장 +1
            var optionIDs = medicalCheckupOptionalReqList.stream()
                    .map(ReservationAddCheckupRequest::getMedicalCheckUpOptionID).collect(Collectors.toList());
            medicalCheckupOptionalRepo.findAllById(optionIDs);
            // Req 객체로부터 AddedCheckup 객체 생성
            var addedCheckupList = medicalCheckupOptionalReqList.stream().map(optionalReq -> {
                var medicalCheckupOptional =
                        medicalCheckupOptionalRepo
                                .findById(optionalReq.getMedicalCheckUpOptionID())
                                .get();

            /*
                DB에 저장된 선택검사 가격
               .multiply(사용자가 입력한 인원)
             */
                var dbOptionAmount = medicalCheckupOptional.getPrice()
                        .multiply(BigDecimal.valueOf(optionalReq.getPersonCount()));

                // 입력 총가격 vs DB계산 가격
                int moneyEquals = optionalReq.getAmount().compareTo(dbOptionAmount);
                if (moneyEquals == 0) {
                    return AddedCheckup.builder()
                            .hospitalReservation(savedHospitalReservation)
                            .medicalCheckupOptional(medicalCheckupOptional)
                            .bookerName(optionalReq.getRequesterName())
                            .amount(dbOptionAmount)
                            .build();
                } else {
                    throw new CustomException("가격 변조 가능성 감지", HttpStatus.BAD_REQUEST);
                }

            }).collect(Collectors.toList());
            addedCheckupRepository.saveAll(addedCheckupList);


            // 선택검진 가격 합산
            for (AddedCheckup addedCheckup:addedCheckupList) {
                addedCheckupAmount = addedCheckupAmount.add(addedCheckup.getAmount());
            }
        }

        // 가격 비교
        BigDecimal totalAmount = programPrice.add(addedCheckupAmount); // 여기서
        hospitalReservation.setAmount(totalAmount);
        return hospitalReservationRepository.save(savedHospitalReservation); // 여기 사이에 select * from account 발생 debug 때문일수도 있음
    }


    @Transactional
    public void integrationReserve(ReservationRequest.Integration reservationDTO) {
        Account account = accountsRepository.getById(CommonUtils.getAuthenticatedUserId());

        // 종합 가격비교는 맨 마지막에서 각 비교는 도메인 예약에서 비교
        // 입력 가격 : 예약금, 투어예약금, 병원예약금, 투어옵션(현재x), 병원옵션
        // 가격비교
        // 계산순서 투어 -> 병원 -> 합산금 = 예약금
        BigDecimal tourTotalAmount = BigDecimal.ZERO;
        BigDecimal hospitalTotalAmount = BigDecimal.ZERO;
        BigDecimal inputTotalAmount = reservationDTO.getTotalAmount();

        var reservation = Reservation.builder()
                .account(account)
//              .reservationTime()
                .title(reservationDTO.getReservationName())
                .amount(reservationDTO.getTotalAmount())
                .status(Reservation.Status.B)
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
        // 투어 예약 서버에서 처리도 생각해보기
        if (isTour){
            TourReservation tourReservation = reserveTour(reservationDTO.getTour(),savedReservation);
            savedReservation.setTourReservation(tourReservation);
            // 투어
            tourTotalAmount = reservation.getTourReservation().getAmount();
        }

        // 검진예약
        if (hospital) {
            // 병원 예약자 확인
            var hospitalBookers = bookerList.stream().filter(Booker::isHospitalReserved).collect(Collectors.toList());

            // 예약
            HospitalReservation hospitalReservation = reserveHospital(reservationDTO.getHospital(),hospitalBookers);
            savedReservation.setHospitalReservation(hospitalReservation);
            // 검진
            hospitalTotalAmount = reservation.getHospitalReservation().getAmount();
        }
        if(!(isTour || hospital)){
            throw new CustomException("예약 정보 없음",HttpStatus.BAD_REQUEST);
        }

        // 최종
        int result = inputTotalAmount.compareTo(tourTotalAmount.add(hospitalTotalAmount));
        if (result != 0){
            throw new CustomException("입력한 가격과 원본 상품가격과 같지 않습니다. ",HttpStatus.BAD_REQUEST);
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
       List<Reservation> reservationList= reservationRepository.findMyReservationsWithStatus(CommonUtils.getAuthenticatedUserId(),pageable);
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

    @Transactional(readOnly = true)
    public ReservationDtoResponse.RVDetails findMyReservationDetail(String reservationId) {
        var reservationDetailsDTO = new ReservationDtoResponse.RVDetails();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Account.UserRole.ROLE_ADMIN);
        Reservation reservation = null;
        // 통합예약
        if (isAdmin){
            reservation = reservationRepository.findMyReservationInfo(reservationId);
        }else {
            reservation = reservationRepository.findMyReservationInfo(reservationId,CommonUtils.getAuthenticatedUserId());
        }

        // dto 변환
        var dto = ReservationDtoResponse.toResponse(reservation);

        // 반려사유 체크
        var rejections = reservation.getReservationRejection();
        if (rejections != null && !rejections.isEmpty()){
            dto.setRejection(reservation.getReservationRejection().get(0).getReason());
        }
        reservationDetailsDTO.setReservationDtoResponse(dto);
        // 병원예약
        if (reservation.getHospitalReservation()!=null){
            var addedCheckupList = addedCheckupRepository.findByHospitalReservationId(reservation.getHospitalReservation().getId());
            var addedCheckupResponses=addedCheckupList.stream().map(addedCheckup -> {
                return AddedCheckupResponse.builder()
                        .id(addedCheckup.getId())
                        .optionName(addedCheckup.getMedicalCheckupOptional().getMedicalCheckupItem().getName())
                        .amount(addedCheckup.getAmount())
                        .bookerName(addedCheckup.getBookerName())
                        .build();
            }).collect(Collectors.toList());
            reservationDetailsDTO.setHospitalReservationDtoResponse(
                    HospitalReservationDtoResponse.toResponse(
                            // 병원예약
                            reservation.getHospitalReservation(),
                            // 추가검진
                            addedCheckupResponses
                    )
            );
        }

        // 투어예약
        var tourReservation = reservation.getTourReservation();
        if (tourReservation!=null){

        reservationDetailsDTO.setTourReservationDtoResponse(
            TourReservationDtoResponse.toResponse(
                    tourReservation
            )
        );
        // 투어 추가 옵션
        reservationDetailsDTO.getTourReservationDtoResponse().setTourOptions(
            tourReservation.getReservationTourOptions().stream().map(reservationTourOptions -> {
                var optionsRes = ReservationTourOptionsRes.toResponse(reservationTourOptions);
                optionsRes.setOptionName(reservationTourOptions.getTourOption().getOptionName());
                return optionsRes;
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

    @Transactional
    public void confirm(String reservationId) {
        var reservation = reservationRepository.findById(reservationId).orElseThrow(() -> {
            throw new CustomException("잘못된 예약번호",HttpStatus.BAD_REQUEST);
        });
        reservation.permit();
//        reservation.setReservationRejection(null);
    }

    @Transactional
    public void reject(String reservationId, ReservationRejectionReq reservationRejectionReq) {
        var reservation = reservationRepository.findById(reservationId).orElseThrow(() -> {
            throw new CustomException("잘못된 예약번호",HttpStatus.BAD_REQUEST);
        });
        reservation.reject(ReservationRejection.builder()
                .reason(reservationRejectionReq.getReason())
                .build()
        );


    }

    @Transactional(readOnly = true)
    public List<ReservationDtoResponse> findAll(Reservation.Status status) {
        List<Reservation> reservationList = null;
        if (status == null) {
            reservationList = reservationRepository.findAll();
        }else {
            reservationList = reservationRepository.findAllByStatus(status);
        }
        return reservationList.stream().map(reservation -> {
                    var dto = ReservationDtoResponse.toResponse(reservation);
                    if (!reservation.getReservationRejection().isEmpty()) {
                        dto.setRejection(reservation.getReservationRejection().get(0).getReason());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void setTourOptionPrice(Long id, BigDecimal newPrice) {
        var revTourOptions = revTourOptionsRepository.findByIdWithTourReservation(id);
        var oldPrice = revTourOptions.getAmount();
        if(oldPrice != null){
            revTourOptions.updateNewPrice(newPrice);
//            throw new CustomException("옵션 가격 수정 API로 다시 시도해주십시오",HttpStatus.BAD_REQUEST);
        }else {
            revTourOptions.setConfirmedAmount(newPrice);
        }

        reservationRepository.findByRevTourOptionId(id).updateTourRevAmount();
    }


    @Transactional
    public void addRevTourOption(String reservationId, ReservationTourOptionsRequest request) {
        var reservation = reservationRepository.findByIdWithTourReservation(reservationId,CommonUtils.getAuthenticatedUserId());
        if (reservation != null && !reservation.getPaymentStatus().equals(Reservation.PaymentStatus.Y)) {
            var reservationTourOptions= request.toReservationTourOptions();
            var tourOption = tourOptionRepository.getById(request.getTourOptionId());

            // 투어 예약에 추가요청사항 등록
            reservation.getTourReservation().addRevTourOption(reservationTourOptions);

            // 투어 예약의 옵션명 등록
            reservationTourOptions.setTourOption(tourOption);

            revTourOptionsRepository.save(reservationTourOptions);
        }
    }

    @Transactional
    public void updateReservationTourOption(Long revTourOptionId, ReservationTourOptionsRequest updateRequest) {
        Reservation reservation = reservationRepository.findByRevTourOptionIdForUpdate(revTourOptionId, CommonUtils.getAuthenticatedUserId());
        if (reservation != null) {

            var tourOptionId = updateRequest.getTourOptionId();
            TourOption tourOption = null;

            // db null check 필요
            if (tourOptionId != null){
                tourOption = tourOptionRepository.getById(updateRequest.getTourOptionId());
            }

            // id 같은거 찾아서 size 1개짜리 list로 변환
            var selected = reservation.getTourReservation().getReservationTourOptions().stream()
                    .filter(reservationTourOptions -> reservationTourOptions.getId().equals(revTourOptionId))
                    .collect(Collectors.toList());

            // 선택한 옵션 변경
            for (ReservationTourOptions options : selected){
                // 관리자가 값을 이미 정한 경우 요청사항을 바꿀 수 없음
                if (options.getAmount() == null){
                    options.updateByRequest(updateRequest);
                    // 만약 투어옵션도 바꾼다면 여기서 변경
                    if (tourOption != null) {
                        options.setTourOption(tourOption);
                    }
                }
            }
        }
    }


}
