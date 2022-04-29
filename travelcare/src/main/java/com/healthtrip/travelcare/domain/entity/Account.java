package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;


@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
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

    @OneToMany()
    private List<ReservationInfo> reservationInfoList;

    public enum UserRole{
        ROLE_COMMON,
        ROLE_ADMIN,
        ROLE_AGENT;

    }
    public enum Status {
        Y,N,B;
    }

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
