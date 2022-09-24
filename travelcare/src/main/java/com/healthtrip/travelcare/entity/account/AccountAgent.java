package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class AccountAgent extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String companyNumber;
    private String companyContact;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Account account;

    public static AccountAgent toEntityBasic(AccountRequest.AgentSignUp agentSignUp) {
        return AccountAgent.builder()
                .name(agentSignUp.getName())
                .companyNumber(agentSignUp.getCompanyNumber())
                .companyContact(agentSignUp.getCompanyContact())
                .build();
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_address_id")
    private AccountAddress companyAddress;

    public void setCompanyAddress(AccountAddress companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setRelation(Account account) {
        this.account = account;
    }
}
