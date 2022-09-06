package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.annotation.DataJpaUnitTest;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.account.AccountAgent;
import com.healthtrip.travelcare.repository.account.AccountAgentRepository;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaUnitTest
class AccountAgentRepositoryTest {
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    AccountAgentRepository accountAgentRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Account account;
    private AccountAgent accountAgent;
    @BeforeEach
    void setup() {
        account = Account.builder()
                .email("test@email.com")
                .password(passwordEncoder.encode("password"))
                .userRole(Account.UserRole.ROLE_COMMON)
                .status(Account.Status.Y)
                .build();
        accountAgent = AccountAgent.builder()
                .account(account)
                .name("The-Air")
                .companyNumber("12345")
                .build();
    }

    @Test
    void save() {
        // given
        accountAgentRepository.save(accountAgent);

        // then
        assertThat(accountAgent.getAccount()).isNotNull();
        assertThat(accountAgent.getAccount().getEmail()).isEqualTo("test@email.com");
        assertThat(passwordEncoder.matches("password",accountAgent.getAccount().getPassword())).isTrue();
        assertThat(accountAgent.getId()).isNotNull();

    }
}