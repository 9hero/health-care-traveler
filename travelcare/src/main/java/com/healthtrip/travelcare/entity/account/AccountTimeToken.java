package com.healthtrip.travelcare.entity.account;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@ToString
@Setter
public class AccountTimeToken {

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String authToken;
    private Boolean expired;
    @ToString.Exclude
    private LocalDateTime expirationDate;

    @Builder
    public AccountTimeToken(String email, String authToken, Boolean expired) {
        this.email = email;
        this.authToken = authToken;
        this.expired = expired;
        this.expirationDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void useToken() {
        this.expired = true;
    }

    public static AccountTimeToken makeToken(String email) {
        return AccountTimeToken.builder()
                .email(email)
                .authToken(UUID.randomUUID().toString().substring(0,5))
                .expired(false)
                .build();
    }

}
