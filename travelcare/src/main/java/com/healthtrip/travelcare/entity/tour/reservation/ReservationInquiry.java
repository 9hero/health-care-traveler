package com.healthtrip.travelcare.entity.tour.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.reservation.Reservation;
import com.healthtrip.travelcare.entity.reservation.ReservationInquiryChat;
import com.healthtrip.travelcare.repository.dto.request.InquiryRequest;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class ReservationInquiry extends BaseTimeEntity {

    @Builder
    public ReservationInquiry(Long id, String title, String question, Reservation reservation, List<ReservationInquiryChat> reservationInquiryChat) {
        this.id = id;
        this.title = title;
        this.question = question;
        this.reservation = reservation;
        this.reservationInquiryChat = reservationInquiryChat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @BatchSize(size = 100)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "reservationInquiry")
    private List<ReservationInquiryChat> reservationInquiryChat = new ArrayList<>();

    public void updateClientRequest(InquiryRequest request) {
        this.title = request.getTitle();
        this.question = request.getQuestion();
    }

}
