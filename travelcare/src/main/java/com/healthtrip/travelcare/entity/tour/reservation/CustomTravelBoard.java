package com.healthtrip.travelcare.entity.tour.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.repository.dto.request.CustomTravelRequest;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@ToString(callSuper = true)
public class CustomTravelBoard extends BaseTimeEntity {

    @Builder
    public CustomTravelBoard(Long id, String title, String question, String answer, TourReservation tourReservation) {
        this.id = id;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.tourReservation = tourReservation;
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
    private TourReservation tourReservation;

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public void updateClientRequest(CustomTravelRequest.ClientModify request) {
        this.title = request.getTitle();
        this.question = request.getQuestion();
    }

}
