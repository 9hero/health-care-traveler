package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private Account account;

    private String refreshToken;

    @Column(name = "expiration_date")
    private LocalDateTime expirationLDT;

    @Transient
    private Date expirationTransient;

    @Autowired
    @Builder
    public RefreshToken(Long id, Account account, String refreshToken, LocalDateTime expirationLDT, Date expirationTransient) {
        this.id = id;
        this.account = account;
        this.refreshToken = refreshToken;
        this.expirationLDT = expirationLDT;
        this.expirationTransient = expirationTransient;
    }
}
