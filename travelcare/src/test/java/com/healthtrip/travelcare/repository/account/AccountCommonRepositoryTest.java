package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.account.AccountAddress;
import com.healthtrip.travelcare.entity.account.AccountCommon;
import com.healthtrip.travelcare.entity.location.Country;
import com.healthtrip.travelcare.entity.reservation.Booker;
import com.healthtrip.travelcare.repository.dto.request.PersonData;
import com.healthtrip.travelcare.repository.location.AddressRepository;
import com.healthtrip.travelcare.repository.location.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class AccountCommonRepositoryTest {
    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    AccountCommonRepository accountCommonRepository;

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    AddressRepository addressRepository;
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Account account;
    private AccountAddress accountAddress;
    private Country country;

    private AccountCommon accountCommon;

    @BeforeEach
    void setup() {
        country = Country.builder()
                .name("USAs")
                .build();
        accountAddress = AccountAddress.builder()
                .country(country)
                .city("new york")
                .district("somewhere")
                .address("anywhere")
                .addressDetail("")
                .postalCode("12345")
                .build();
        account = Account.builder()
                .email("test@email.com")
                .password(passwordEncoder.encode("password"))
                .userRole(Account.UserRole.ROLE_COMMON)
                .status(Account.Status.Y)
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
    }

    AccountAddress setAddress() {
        Country savedCountry = countryRepository.save(country);
        accountAddress.setCountry(savedCountry);
        return addressRepository.save(accountAddress);
    }

    @Test
    @DisplayName("일반 회원 저장")
    void save() {
        // given
        var savedAccount = accountsRepository.save(account);
        AccountAddress savedAddress = setAddress();
        accountCommon.setRelation(savedAddress,savedAccount);

        // when
        AccountCommon savedAccountCommon = accountCommonRepository.save(accountCommon);

        // then
        assertThat(savedAccountCommon).isNotNull();
        assertThat(savedAccountCommon.getUserId()).isNotNull();
        assertThat(savedAccountCommon.getAccount()).isNotNull();
        assertThat(savedAccountCommon.getAccount().getEmail()).isEqualTo("test@email.com");
        assertThat(passwordEncoder.matches("password",accountCommon.getAccount().getPassword())).isTrue();

    }
}