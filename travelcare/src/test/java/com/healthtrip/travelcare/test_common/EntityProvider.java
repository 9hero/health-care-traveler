package com.healthtrip.travelcare.test_common;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.account.AccountAddress;
import com.healthtrip.travelcare.entity.account.AccountAgent;
import com.healthtrip.travelcare.entity.account.AccountCommon;
import com.healthtrip.travelcare.entity.hospital.*;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.entity.tour.PackageTourPayment;
import com.healthtrip.travelcare.entity.tour.reservation.*;
import com.healthtrip.travelcare.entity.tour.tour_package.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class EntityProvider {
    // 필드 이점
    // 관계매핑된 Entity가 나옴, this.메소드 일일이 수정안해도댐
    // 단점: 메모리차지

    // 메소드 이점
    // 각 메소드 커스텀 가능
    // 단점: 일일이 코드수정 필요

    // 엔티티 제공자 답게 기본 엔티티만 주자
    private final Country country;
    private final AccountAddress accountAddress;
    private final TourBookerAddress tourBookerAddress;
    private final Account account;
    private final AccountAgent accountAgent;
    private final AccountCommon accountCommon;
    private final TourPackage tourPackage;
    private final TourPackageDate tourPackageDate;
    private final TourReservation tourReservation;
    private final TourBooker tourBooker;
    private final PackageTourPayment packageTourPayment;

    private final HospitalAddress hospitalAddress;
    private final Hospital hospital;
    private final MedicalCheckupCategory medicalCheckupCategory;
    private final MedicalCheckupProgram medicalCheckupProgram;
    private final MedicalCheckupItem medicalCheckupItem;
    private final MedicalCheckupOptional medicalCheckupOptional;
    private final ProgramCategory programCategory;
    private final ProgramCheckupItem programCheckupItem;
    private final TourItinerary tourItinerary;
    private final TourPlace tourPlace;
    private final TourPlaceImage tourPlaceImage;
    private final TourItineraryElement tourItineraryElement;
    private final MedicalCheckupItemCategory medicalCheckupItemCategory;
    private final TourPackageFile tourPackageFile;
    private final HospitalReservation hospitalReservation;
    private final Reservation reservation;
    private final Booker booker;
    private final TourOption tourOption;

    public EntityProvider() {

        // 장소
        country = Country.builder()
                .name("Unite of states")
                .build();
        accountAddress = AccountAddress.builder()
                .country(country)
                .city("new york")
                .district("somewhere")
                .address("anywhere")
                .addressDetail("")
                .postalCode("12345")
                .build();
        tourBookerAddress = TourBookerAddress.builder()
                .country(country)
                .city("new york")
                .district("somewhere")
                .address("anywhere")
                .addressDetail("")
                .postalCode("12345")
                .build();

        // 계정관련
        account =Account.builder() // account agent or common 하나만
                .email("test@num1")
                .status(Account.Status.Y)
                .password("1234")
                .userRole(Account.UserRole.ROLE_COMMON)
                .build();
        accountCommon = AccountCommon.builder()
                .account(account)
                .birth(LocalDate.of(1999,4,14))
                .phone("010-3123-4321")
                .emergencyContact("010-4321-3123")
                .firstName("json")
                .lastName("kim")
                .gender(Booker.Gender.M)
                .build();
        accountAgent = AccountAgent.builder()
                .account(account)
                .name("The-Air")
                .companyNumber("12345")
                .build();

        // 여행

        tourPackage= TourPackage.builder()
                .account(account)
                .title("testTitle")
                .description("test")
                .prices(TourPackagePrices.builder()
                        .adultPrice(BigDecimal.TEN)
                        .childPrice(BigDecimal.ONE)
                        .infantPrice(BigDecimal.ZERO).build()
                )
                .standardOffer("기본제공사항")
                .nonOffer("불포함사항")
                .build();
        tourPackageFile= TourPackageFile.builder()
                .tourPackage(tourPackage)
                .fileName("중복없는이름")
                .fileSize(1223)
                .originalName("파일의이름")
                .url("대충url")
                .build();
        tourPackage.setMainImage(tourPackageFile);
        tourPackageDate =
                TourPackageDate.builder()
                        .tourPackage(tourPackage)
                        .departAt(LocalDateTime.now())
                        .currentNumPeople((short)5)
                        .arriveAt(LocalDateTime.now().plusDays(1L))
                        .peopleLimit((short) 30)
                        .build();
        tourReservation = TourReservation.builder()
                .tourPackage(tourPackage)
                .amount(BigDecimal.TEN)
                .personCount((short) 1)
                .reservedTime(LocalDateTime.now())
                .build();
        tourBooker = TourBooker.builder()
                .tourReservation(tourReservation)
                .address(tourBookerAddress)
                .birth(LocalDate.now())
                .emergencyContact("1111")
                .phone("1111")
                .lastName("james")
                .firstName("borne")
                .gender(Booker.Gender.M)
                .build();
        packageTourPayment = PackageTourPayment.builder()
                .tourReservation(tourReservation)
                .amount(BigDecimal.valueOf(550L))
                .paymentDate(LocalDateTime.now())
                .currency("USD")
                .payType("CARD")
                .build();
        // 투어 일정
        tourItinerary = TourItinerary.builder()
                .tourPackage(tourPackage)
                .day("1일차")
                .accommodation("5성 호텔")
                .details("세부적일정입니다")
                .notice("유의사항입니다")
                .location("해운대구")
                .specificLocations("해동용궁사, 해운대바다, ~~ 등")
                .build();
        tourItineraryElement = TourItineraryElement.builder()
                .tourItinerary(tourItinerary)
                .sequence((short)1)
                .title("해운대 출발")
                .elementType(TourItineraryElement.ElementType.MOVE)
                .build();
        tourItinerary.addItineraryElement(tourItineraryElement);
        tourPlace= TourPlace.builder()
                .placeName("장소명")
                .description("장소 설명")
                .summery("장소 짧은 소개")
                .build();
        tourPlaceImage = TourPlaceImage.builder()
                .tourPlace(tourPlace)
                .fileName("uniqueFileName")
                .fileSize(1223)
                .originalName("파일의이름")
                .url("대충 url")
                .build();
        //  병원 Entity

        hospitalAddress = HospitalAddress.builder()
                .address("부산 병원")
                .addressDetail("대로변")
                .country(country)
                .city("해운대")
                .district("지역")
                .postalCode("12345")
                .build();
        hospital = Hospital.builder()
                .hospitalAddress(hospitalAddress)
                .name("병원 이름")
                .description("병원 설명")
                .build();
        medicalCheckupCategory = MedicalCheckupCategory.builder()
                .name("기본 검사")
                .build();
        medicalCheckupProgram = MedicalCheckupProgram.builder()
                .hospital(hospital)
                .programName("임시 검진")
                .programType(MedicalCheckupProgram.ProgramType.Total)
                .priceForMan(BigDecimal.ONE)
                .priceForWoman(BigDecimal.TEN)
//              need mapping for test  .programCategories(List.of())
                .build();
        programCategory = ProgramCategory.builder()
                .medicalCheckupProgram(medicalCheckupProgram)
                .medicalCheckupCategory(medicalCheckupCategory)
                .build();
        medicalCheckupProgram.addCategory(programCategory);
        medicalCheckupItem = MedicalCheckupItem.builder()
                .name("결과 상담")
                .build();
        medicalCheckupItemCategory = MedicalCheckupItemCategory.builder()
                .medicalCheckupItem(medicalCheckupItem)
                .medicalCheckupCategory(medicalCheckupCategory)
                .build();
        medicalCheckupItem.addCategory(medicalCheckupItemCategory);
        medicalCheckupOptional = MedicalCheckupOptional.builder()
                .hospital(hospital)
                .medicalCheckupItem(medicalCheckupItem)
                .price(BigDecimal.ONE)
                .build();
        programCheckupItem = ProgramCheckupItem.builder()
                .medicalCheckupItem(medicalCheckupItem)
                .medicalCheckupProgram(medicalCheckupProgram)
                .build();
        hospitalReservation = HospitalReservation.builder()
                .amount(BigDecimal.TEN)
                .personCount((short) 2)
                .reservedTime(LocalDateTime.now())
                .medicalCheckupProgram(medicalCheckupProgram)
                .build();

        // 예약
        reservation = Reservation.builder()
                .account(account)
                .title("예약명")
                .tourReservation(tourReservation)
                .hospitalReservation(hospitalReservation)
                .amount(BigDecimal.TEN)
//                .booker(entityProvider.getBooker())
                .status(Reservation.Status.Y)
                .manCount((short) 2)
                .paymentStatus(Reservation.PaymentStatus.Y)
                .build();
        reservation.idGenerate();
        booker = Booker.builder()
                .birth(LocalDate.now())
                .firstName("first")
                .lastName("last")
                .gender(Booker.Gender.M)
                .phone("phone")
                .emergencyContact("emergency")
                .reservation(reservation)
                .tourReserved(true)
                .hospitalReserved(false)
                .simpleAddress("한줄주소")
                .build();
        tourOption = TourOption.builder()
                .optionName("렌트카")
                .build();
    }

    public TourReservation getNewTourResrvation() {
        return TourReservation.builder()
                .reservedTime(LocalDateTime.now())
                .amount(BigDecimal.TEN)
                .personCount((short) 1)
                .build();
    }
}
