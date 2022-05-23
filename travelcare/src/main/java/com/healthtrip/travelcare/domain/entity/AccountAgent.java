package com.healthtrip.travelcare.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
public class AccountAgent {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account account;
}
