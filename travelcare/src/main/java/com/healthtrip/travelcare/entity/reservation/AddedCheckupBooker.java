package com.healthtrip.travelcare.entity.reservation;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "checkup_option_booker")
public class AddedCheckupBooker extends BaseTimeEntity {

    @Builder
    public AddedCheckupBooker(Long id, AddedCheckup addedCheckup, Booker booker) {
        this.id = id;
        this.addedCheckup = addedCheckup;
        this.booker = booker;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    private AddedCheckup addedCheckup;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    private Booker booker;

}
