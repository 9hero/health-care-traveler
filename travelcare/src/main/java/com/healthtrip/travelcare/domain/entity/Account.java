package com.healthtrip.travelcare.domain.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;


@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@ToString(callSuper = true)
public class Account extends BaseTimeEntity{
    @Builder
    public Account(Long id, String email, String password, Status status, UserRole userRole, List<NoticeBoard> noticeBoard, List<ReservationInfo> reservationInfoList) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.userRole = userRole;
        this.noticeBoard = noticeBoard;
        this.reservationInfoList = reservationInfoList;
    }


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

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ReservationInfo> reservationInfoList;


    // 단방향도 괜찮을것같음
//    @OneToOne(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private AccountCommon accountCommon;
//
//    @OneToOne(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private AccountAgent accountAgent;

    public enum UserRole{
        ROLE_COMMON,
        ROLE_ADMIN,
        ROLE_AGENT;

    }
    public enum Status {
        Y,N,B;
    }

//    public void setAccountCommon(AccountCommon accountCommon) {
//        this.accountCommon = accountCommon;
//    }

    public void toAdmin() {
        this.userRole = UserRole.ROLE_ADMIN;
    }

    public void toAgent() {
        this.userRole = UserRole.ROLE_AGENT;
    }

    public void accountConfirm() {
        this.status = Status.Y;
    }

    public void accountBlock() {
        this.status = Status.B;
    }

}


