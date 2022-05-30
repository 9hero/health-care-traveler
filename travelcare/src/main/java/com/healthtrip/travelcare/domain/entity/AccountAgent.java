package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
public class AccountAgent extends BaseTimeEntity{

    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String companyNumber;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Account account;

    public static AccountAgent toEntityBasic(AccountRequest.agentSignUp agentSignUp) {
        return AccountAgent.builder()
                .name(agentSignUp.getName())
                .companyNumber(agentSignUp.getCompanyNumber())
                .build();
    }

    public void setRelation(Account account) {
        this.account = account;
    }
}
