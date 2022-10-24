package com.healthtrip.travelcare.entity.account;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.entity.GeneralFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Tendency extends BaseTimeEntity {

    @Builder
    public Tendency(Long id, String name, String description, ScoreLevel extroversionLevel, ScoreLevel opennessLevel, ScoreLevel friendlinessLevel, GeneralFile imageFile) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.extroversionLevel = extroversionLevel;
        this.opennessLevel = opennessLevel;
        this.friendlinessLevel = friendlinessLevel;
        this.imageFile = imageFile;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ScoreLevel extroversionLevel;

    @Enumerated(EnumType.STRING)
    private ScoreLevel opennessLevel;

    @Enumerated(EnumType.STRING)
    private ScoreLevel friendlinessLevel;

    @JoinColumn
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,optional = false)
    private GeneralFile imageFile;

    public void setImageFile(GeneralFile imageFile) {
        this.imageFile = imageFile;
    }

    @Getter
    public enum ScoreLevel {
        H("상"),
        L("하");
        private final String level;
        ScoreLevel(String level) {
            this.level = level;
        }
    }
}
