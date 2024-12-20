package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.NoticeBoard;
import com.healthtrip.travelcare.entity.tour.reservation.TourReservation;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Getter
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true,exclude = {"noticeBoard"})
public class Account extends BaseTimeEntity implements UserDetails {

    @Builder
    public Account(Long id, String email, String password, Status status, UserRole userRole, List<NoticeBoard> noticeBoard) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.userRole = userRole;
        this.noticeBoard = noticeBoard;
        authorities = new HashSet<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password; // bcrypt 필수적용

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "account")
    @ToString.Exclude
    private List<NoticeBoard> noticeBoard;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    // --------ROLES-----------
    @Setter
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    // UserDetailsService loadUserByUserName 에서 Set userRole
    // 이유: 우리는 1계정 1권한이고 아직 많은 권한이 필요하지 않음
    @Transient
    private Set<UserRole> authorities;

    public void addAuthorities(UserRole userRole){
        if (authorities == null){
            authorities = new HashSet<>();
        }
        authorities.add(userRole);
    }
    public enum UserRole implements GrantedAuthority{
        ROLE_COMMON,
        ROLE_ADMIN,
        ROLE_AGENT;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    public enum Status {
        Y,N,B;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == Status.Y || this.status == Status.N;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == Status.Y || this.status == Status.N;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == Status.Y || this.status == Status.N;
    }

    @Override
    public boolean isEnabled() {
        return this.status == Status.Y || this.status == Status.N;
    }


    public void resetPasswordAs(String password) {
        this.password = password;
    }
    public void toAdmin() {
        this.userRole = UserRole.ROLE_ADMIN;
    }

    public void toAgent() {
        this.userRole = UserRole.ROLE_AGENT;
    }

    public void accountConfirm() {
        if (this.status == Status.N)
        this.status = Status.Y;
    }

    public void accountBlock() {
        this.status = Status.B;
    }

}


