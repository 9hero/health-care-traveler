package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.account.Account;
import com.healthtrip.travelcare.entity.tour.reservation.ReservationInquiry;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ReservationInquiryChat extends BaseTimeEntity {

    @Builder
    public ReservationInquiryChat(Long id,
                                  ReservationInquiry reservationInquiry,
                                  Writer writer, String chat, Account account) {
        this.id = id;
        this.reservationInquiry = reservationInquiry;
        this.writer = writer;
        this.chat = chat;
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReservationInquiry reservationInquiry;

    @Enumerated(EnumType.STRING)
    private Writer writer;
    private String chat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account account;

    public void modifyChat(String chat) {
        this.chat = chat;
    }

    @Getter
    public enum Writer{
        A("관리자"),
        C("고객");

        private final String writerName;

        Writer(String writerName) {
            this.writerName = writerName;
        }
    }
}
