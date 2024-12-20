package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Personality extends BaseTimeEntity {

    @Builder
    public Personality(Long id, Tendency tendency, Float friendlinessScore, Float extroversionScore, Float opennessScore) {
        this.id = id;
        this.tendency = tendency;
        this.friendlinessScore = friendlinessScore;
        this.extroversionScore = extroversionScore;
        this.opennessScore = opennessScore;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Tendency tendency;

    private Float friendlinessScore;

    private Float extroversionScore;

    private Float opennessScore;
}
