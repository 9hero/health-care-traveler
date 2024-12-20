package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class AccountsRepositoryTest {

    @Autowired
    AccountsRepository accountsRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Account account;

    @BeforeEach
    void setup() {
        account = Account.builder()
                .email("test@email.com")
                .password(passwordEncoder.encode("password"))
                .userRole(Account.UserRole.ROLE_COMMON)
                .status(Account.Status.Y)
                .build();
    }
    @Test
    @DisplayName("회원 객체 저장")
    void account_save_savedAccount() {
        // given

        // when
        var savedAccount = accountsRepository.save(account);
        // then
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isGreaterThan(0);
    }
    @Test
    @DisplayName("회원 객체 조회")
    void account_find_foundAccount() {
        // given
        var savedAccount = accountsRepository.save(account);

        // when
        var foundAccount = accountsRepository.findById(savedAccount.getId()).get();
        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isGreaterThan(0);
        assertThat(foundAccount).isEqualTo(savedAccount);
    }
}