package com.healthtrip.travelcare.test_common;

import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.account.AccountAddress;
import com.healthtrip.travelcare.entity.account.AccountAgent;
import com.healthtrip.travelcare.entity.account.AccountCommon;
import com.healthtrip.travelcare.entity.hospital.*;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.tour.PackageTourPayment;
import com.healthtrip.travelcare.entity.tour.reservation.TourBookerAddress;
import com.healthtrip.travelcare.entity.tour.reservation.TourPackageDate;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import com.healthtrip.travelcare.entity.tour.reservation.TourBooker;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
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

    public EntityProvider() {

        // 장소
        country = Country.builder()
                .name("USA")
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
                .gender(PersonData.Gender.M)
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
                .price(BigDecimal.TEN)
                .type("test")
                .build();
        tourPackageDate =
                TourPackageDate.builder()
                        .tourPackage(tourPackage)
                        .departAt(LocalDateTime.now())
                        .currentNumPeople((short)5)
                        .arriveAt(LocalDateTime.now().plusDays(1L))
                        .peopleLimit((short) 30)
                        .build();
        tourReservation = TourReservation.builder()
                .account(account)
                .tourPackageDate(tourPackageDate)
                .amount(BigDecimal.TEN)
                .csStatus(TourReservation.CsStatus.K)
                .paymentStatus(TourReservation.PaymentStatus.N)
                .status(TourReservation.Status.Y)
                .personCount((short) 1)
                .build();
        tourBooker = TourBooker.builder()
                .tourReservation(tourReservation)
                .address(tourBookerAddress)
                .birth(LocalDate.now())
                .emergencyContact("1111")
                .phone("1111")
                .lastName("james")
                .firstName("borne")
                .gender(PersonData.Gender.M)
                .build();
        packageTourPayment = PackageTourPayment.builder()
                .tourReservation(tourReservation)
                .amount(BigDecimal.valueOf(550L))
                .paymentDate(LocalDateTime.now())
                .currency("USD")
                .payType("CARD")
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
                .medicalCheckupCategory(medicalCheckupCategory)
                .name("결과 상담")
                .build();
        medicalCheckupOptional = MedicalCheckupOptional.builder()
                .hospital(hospital)
                .medicalCheckupItem(medicalCheckupItem)
                .priceForMan(BigDecimal.ONE)
                .priceForWoman(BigDecimal.TEN)
                .build();
        programCheckupItem = ProgramCheckupItem.builder()
                .medicalCheckupItem(medicalCheckupItem)
                .medicalCheckupProgram(medicalCheckupProgram)
                .build();
    }

    public TourReservation getNewTourResrvation() {
        return TourReservation.builder()
//                .tourReservationPeople()
                .account(account)
                .tourPackageDate(tourPackageDate)
                .amount(BigDecimal.TEN)
                .csStatus(TourReservation.CsStatus.K)
                .paymentStatus(TourReservation.PaymentStatus.N)
                .status(TourReservation.Status.Y)
                .personCount((short) 1)
                .build();
    }
}
