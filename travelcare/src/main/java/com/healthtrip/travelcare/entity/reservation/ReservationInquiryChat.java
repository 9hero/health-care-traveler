package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
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
                                  Writer writer, String chat) {
        this.id = id;
        this.reservationInquiry = reservationInquiry;
        this.writer = writer;
        this.chat = chat;
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

    @Getter
    public enum Writer{
        admin,
        customer
    }
}
