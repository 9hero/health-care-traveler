package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.config.security.jwt.JwtProvider;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.account.RefreshTokenRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @Transactional
    @Disabled
    void saveAndLoad() {
        System.out.println(refreshTokenRepository.findByIdAndAccountId(77L, 128L));
//        var acc = accountsRepository.getById(128L);
//        var refreshToken = RefreshToken.builder()
//                .account(acc)
//                .refreshToken("test")
//                .expirationLDT(LocalDateTime.now())
//                .build();
//        var refreshToken = jwtProvider.issueRefreshToken(acc);
//        var t = refreshTokenRepository.save(refreshToken);

//        System.out.println(t.getId()+"  "+t);
//        System.out.println(refreshTokenRepository.findByIdAndAccountId(t.getId(),128L));
    }
}