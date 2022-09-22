package com.healthtrip.travelcare.entity.tour.tour_package;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TourItineraryElement {

    @Builder
    public TourItineraryElement(Long id, TourItinerary tourItinerary, List<TourPlaceList> tourPlaceLists, String title, ShowType showType, Short sequence) {
        this.id = id;
        this.tourItinerary = tourItinerary;
        this.tourPlaceLists = tourPlaceLists;
        this.title = title;
        this.showType = showType;
        this.sequence = sequence;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
//    @JoinColumn
    private TourItinerary tourItinerary;

    // 구성요소의 장소목록 (장소와 일정구성요소 중간 테이블)
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "tourItineraryElement",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<TourPlaceList> tourPlaceLists;

    private String title;

    // front에서 사용할 타입 게시물타입, 이동타입, 정보타입
    @Enumerated(EnumType.STRING)
    private ShowType showType;

    // 일정 순서 1:도착 -> 2:이동 -> 3....n:종료
    private Short sequence;
    public enum ShowType {
        MOVE
    }
}
