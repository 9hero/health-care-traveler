package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.dto.request.CustomTravelRequest;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@ToString(callSuper = true)
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

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public void updateClientRequest(CustomTravelRequest.ClientModify request) {
        this.title = request.getTitle();
        this.question = request.getQuestion();
    }

}
