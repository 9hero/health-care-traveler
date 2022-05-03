package com.healthtrip.travelcare.domain.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CustomTravelBoard extends BaseTimeEntity{

    @Builder
    public CustomTravelBoard(Long id, String title, String question, String answer, ReservationInfo reservationInfo) {
        this.id = id;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.reservationInfo = reservationInfo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private ReservationInfo reservationInfo;


}
