package com.healthtrip.travelcare.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password; // bcrypt 필수적용

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "account")
    @ToString.Exclude
    private List<NoticeBoard> noticeBoard;

    public enum UserRole{
        ROLE_COMMON,
        ROLE_ADMIN,
        ROLE_AGENT;


    }
    public enum Status {
        Y,N,B;
    }

}
