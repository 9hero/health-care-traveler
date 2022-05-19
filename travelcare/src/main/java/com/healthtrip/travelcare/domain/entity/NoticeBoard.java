package com.healthtrip.travelcare.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class NoticeBoard extends BaseTimeEntity{

    @Builder
    public NoticeBoard(Long id, Account account, String title, String announcement) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.announcement = announcement;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Account account;

    private String title;

    private String announcement;

    public void update(String title,String announcement) {
        this.title =title;
        this.announcement = announcement;
    }
}
